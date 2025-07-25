import { useDispatch } from 'react-redux'
import { addCartItem, loadCartItemsFromLocal } from '../store/slices/cartSlice'
import { addWishItem } from '../store/slices/wishListSlice'
import { Link, useNavigate, useOutletContext } from 'react-router-dom'
import { useEffect, useState } from 'react'
import { deleteProduct } from '../store/slices/productsSlice'

export default function Product({ productId, title, rating, price, imageUrl }) {
  const username = localStorage.getItem('username')
  const dispatch = useDispatch()
  const navigate = useNavigate() // For navigation to UpdateProduct page
  const [, , , , ,] = useOutletContext()
  const [productStock, setProductStock] = useState(null)
  useEffect(() => {
    const fetchProductCount = async () => {
      try {
        const response = await fetch(
          `http://localhost:8080/api/products/${productId}`
        )
        if (response.ok) {
          const data = await response.json()
          setProductStock(data.rating?.count || 0)
        } else {
          console.error('Failed to fetch product stock')
        }
      } catch (error) {
        console.error('Error fetching product:', error)
      }
    }

    fetchProductCount()
  }, [productId])
  // Handle delete for admin
  const isAdmin = localStorage.getItem('isAdmin')
  const handleDelete = (productId) => {
    // Send DELETE request to the backend
    console.log(productId)

    fetch(`http://localhost:8080/api/products/${productId}`, {
      method: 'DELETE',
    })
      .then((response) => {
        if (response.ok) {
          // If deletion was successful, update Redux store

          // Dispatch the deleteProduct action to update Redux state
          dispatch(deleteProduct(productId))

          // Optional: Remove the deleted product from localStorage
          const updatedProducts =
            JSON.parse(localStorage.getItem('productsList')) || []
          const newProductList = updatedProducts.filter(
            (product) => product.id !== productId
          )
          localStorage.setItem('productsList', JSON.stringify(newProductList))
        } else {
          console.error('Failed to delete product')
        }
      })
      .catch((error) => {
        console.error('Error deleting product:', error)
      })
  }

  // database

  const [userId, setUserId] = useState(null)
  //   // console.log(userId);
  useEffect(() => {
    const fetchUserId = async () => {
      try {
        // Step 1: Retrieve the username from localStorage
        const username = localStorage.getItem('username')
        if (!username) {
          console.warn('Username not found in localStorage')
          return // Early return if username is not found, so the rest of the code doesn't run
        }

        // Step 2: Fetch user details by username to get the userId
        const userResponse = await fetch(
          `http://localhost:8080/api/users/${username}`
        )

        if (!userResponse.ok) {
          console.error('User not found')
          return
        }

        const user = await userResponse.json() // Assuming the response contains user details including ID
        setUserId(user.id) // Set userId in state
      } catch (error) {
        console.error('Error:', error)
      }
    }

    const username = localStorage.getItem('username')
    if (username) {
      fetchUserId() // Only call fetchUserId if username exists
    }
  }, [])

  // Add to cart function
  const addToCart = async (productId, quantity = 1) => {
    const cartKey = 'cartItems' // Key for localStorage

    if (!userId) {
      // User is not logged in; manage cart in localStorage
      let storedCart = JSON.parse(localStorage.getItem(cartKey)) || []
      const existingProductIndex = storedCart.findIndex(
        (item) => item.productId === productId
      )

      if (existingProductIndex !== -1) {
        storedCart[existingProductIndex].quantity += quantity // Update quantity
      } else {
        storedCart.push({ productId, quantity }) // Add new product
      }

      // Save updated cart to localStorage
      localStorage.setItem(cartKey, JSON.stringify(storedCart))

      // Dispatch to update Redux store
      dispatch(addCartItem({ productId }))
    } else {
      try {
        // Step 1: Add item to the cart on the backend
        const response = await fetch(
          `http://localhost:8080/api/cart/add?userId=${userId}&productId=${productId}&quantity=${quantity}`,
          { method: 'POST' }
        )

        if (!response.ok) {
          alert('❌ Failed to add item to cart')
          return
        }

        // Step 2: Dispatch the action after successful addition
        dispatch(addCartItem({ productId }))

        // Step 3: Update stock in DB
        // const stockRes = await fetch(
        //   `http://localhost:8080/api/products/stock/${productId}/-1`,
        //   { method: 'PUT' }
        // )

        // if (!stockRes.ok) {
        //   console.error('❌ Failed to update stock in DB after adding to cart.')
        //   return
        // }

        // setProductStock((prev) => Math.max(prev - 1, 0))
      } catch (error) {
        console.error('❌ Error adding item to cart:', error)
        alert('❌ Failed to add item to cart')

        return
      }
    }
  }

  // Handle add to wishlist
  const handleAddToWishList = async () => {
    const wishKey = 'wishItems' // Use username-based key or a default key

    if (!userId) {
      // User is not logged in; manage wishlist in localStorage
      let storedWish = JSON.parse(localStorage.getItem(wishKey)) || []
      const existingProductIndex = storedWish.findIndex(
        (item) => item.productId === productId
      )

      if (existingProductIndex === -1) {
        storedWish.push({ productId, quantity: 1 }) // Add new product
      } else {
        console.log('Product already in wishlist.')
      }

      // Save updated wishlist to localStorage
      localStorage.setItem(wishKey, JSON.stringify(storedWish))

      // Dispatch to update Redux store
      dispatch(addWishItem({ productId }))
      console.log('Added item to localStorage wishlist:', storedWish)
    } else {
      console.log(userId)
      try {
        // Step 1: Add item to the wishlist on the backend
        const wishlistItem = { userId, productId, quantity: 1 } // Construct the item to send

        const response = await fetch(
          `http://localhost:8080/api/wishlist/add?userId=${userId}&productId=${productId}`,
          {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json', // Send JSON data
            },
            body: JSON.stringify(wishlistItem), // Send the wishlist item object as the request body
          }
        )

        if (response.ok) {
          // Step 2: Dispatch the action after successful addition
          dispatch(addWishItem({ productId }))
        } else {
          console.error('Failed to add item to wishlist')
        }
      } catch (error) {
        console.error('Error adding item to wishlist:', error)
      }
    }
  }

  // Handle update product (for admin)
  const handleUpdateProduct = () => {
    navigate(`/update-product/${productId}`, {
      state: { productId, title, rating, price, imageUrl },
    })
  }

  return (
    <div className="product">
      <div className="product-image">
        <Link to={`/${productId}`}>
          <img src={imageUrl} alt={title} />
        </Link>
      </div>
      <div className="title-container">
        <Link to={`/${productId}`}>
          <h3 className="item-detail">{title}</h3>
        </Link>
      </div>
      <div className="price-rating-container">
        <p className="rating">{+rating.rate} ★ ★ ★ ★</p>
        {localStorage.getItem('isAdmin') === 'true' && (
          <p
            className="price"
            style={
              productStock === 0 ? { color: 'red', fontWeight: 'bold' } : {}
            }
          >
            {productStock === 0 ? 'Out of Stock' : `Stock: ${productStock}`}
          </p>
        )}
        <p className="price">${price}</p>
      </div>
      <div className="cta-container">
        {localStorage.getItem('isAdmin') === 'true' ? (
          <>
            <button onClick={() => handleDelete(productId)}>Remove </button>
            <button onClick={handleUpdateProduct}>Edit </button>
          </>
        ) : (
          <>
            {productStock > 0 ? (
              <button onClick={() => addToCart(productId, 1)}>
                Add to Cart
              </button>
            ) : (
              <button
                disabled
                style={{ backgroundColor: '#ccc', cursor: 'not-allowed' }}
              >
                Out of Stock
              </button>
            )}
            <button onClick={handleAddToWishList}>Add to WishList</button>
          </>
        )}
      </div>
    </div>
  )
}
