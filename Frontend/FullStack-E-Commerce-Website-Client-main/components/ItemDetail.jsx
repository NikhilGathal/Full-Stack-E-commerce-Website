import React, { useEffect, useState } from 'react'
import { useOutletContext, useParams } from 'react-router-dom'
import './ItemDetail.css'
import { useDispatch } from 'react-redux'
import { addCartItem } from '../store/slices/cartSlice'
import { addWishItem } from '../store/slices/wishListSlice'
import Footer from './Footer'
import { useNavigate } from 'react-router-dom'
import { deleteProduct } from '../store/slices/productsSlice'
const ItemDetail = () => {
  const dispatch = useDispatch()
  let { productId } = useParams() // Get the itemId from the URL
  productId = +productId // Convert productId to a number
  const [item, setItem] = useState(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)
  const [, dark] = useOutletContext()
  const [pdt, setpdt] = useState([])
  const [userId, setUserId] = useState(null)
  const navigate = useNavigate()

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

  const handleUpdateProduct = () => {
    navigate(`/update-product/${productId}`, {
      state: {
        productId,
        title: item.title,
        rating: item.rating,
        price: item.price,
        imageUrl: item.image,
      },
    })
  }

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
    setTimeout(() => navigate('/Home'), 1000)
  }

  useEffect(() => {
    // Fetch item details from the backend API
    const fetchItemFromLocalStorage = () => {
      try {
        fetch('http://localhost:8080/api/products')
          .then((res) => res.json())
          .then((data) => {
            // Update pdt state with fetched data
            setpdt(data)
          })
      } catch (err) {
        setError(err.message) // Handle error
        setLoading(false) // Set loading to false
      }
    }

    fetchItemFromLocalStorage()
  }, []) // Only run once on mount

  useEffect(() => {
    // console.log(pdt);

    if (pdt.length > 0) {
      const product = pdt.find((p) => p.id === productId) // Find the product by ID
      if (!product) {
        throw new Error('Product not found')
      }
      setItem(product) // Set the product as the state
      setLoading(false) // Set loading to false
    }
  }, [pdt, productId]) // Runs when pdt is updated

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
      } // Save updated cart to localStorage

      localStorage.setItem(cartKey, JSON.stringify(storedCart)) // Dispatch to update Redux store

      dispatch(addCartItem({ productId }))
      console.log('Added item to localStorage cart:', storedCart)
    } else {
      try {
        // Step 1: Add item to the cart on the backend
        const response = await fetch(
          `http://localhost:8080/api/cart/add?userId=${userId}&productId=${productId}&quantity=${quantity}`,
          {
            method: 'POST',
          }
        )

        if (!response.ok) {
          console.error('❌ Failed to add item to cart')
          return // ⛔ Stop here if the API call failed
        } // Step 2: Dispatch the action after successful addition

        dispatch(addCartItem({ productId })) // Step 3: Update the stock only if cart addition succeeded

        // const stockRes = await fetch(
        //   `http://localhost:8080/api/products/stock/${productId}/-1`,
        //   { method: 'PUT' }
        // )

        // if (stockRes.ok) {
        //   setProductStock((prev) => Math.max(prev - 1, 0))
        // } else {
        //   alert('❌ Failed to update stock after adding to cart.')
        //   return
        // }
      } catch (error) {
        console.error('❌ Error adding item to cart:', error)
        return // ⛔ Stop execution if an exception occurred
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

  if (loading)
    return (
      <>
        {' '}
        <div className="error-msg">Loading...</div> <Footer dark={dark} />{' '}
      </>
    )
  if (error)
    return (
      <>
        {' '}
        <div className="error-msg">Error: {error}</div> <Footer dark={dark} />{' '}
      </>
    )

  return (
    <>
      <div className={`item-detail-container ${dark ? 'dark' : ''}`}>
        <div className="item-image">
          <img src={item.image} alt={item.title} />
        </div>
        <div className="item-info">
          <h1>{item.title}</h1>
          <p className="item-price">
            ${item.price ? parseFloat(item.price).toFixed(2) : 'N/A'}
          </p>
          <p className="item-description">{item.description}</p>
          <p className="item-category">Category: {item.category}</p>
          <div className="item-rating">
            <span>
              Rating: {item.rating.rate} / 5 ({item.rating.count} reviews)
            </span>
          </div>

          <div className="item-button">
            {localStorage.getItem('isAdmin') === 'true' ? (
              <>
                <button onClick={() => handleDelete(productId)}>
                  Remove Product
                </button>

                <p
                  className="outofs"
                  style={
                    productStock === 0
                      ? { color: 'red', fontWeight: 'bold' }
                      : {}
                  }
                >
                  {productStock === 0
                    ? 'Out of Stock'
                    : `Stock: ${productStock}`}
                </p>

                <button onClick={handleUpdateProduct}>Edit Product</button>
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
      </div>
      {/* <Footer dark={dark} /> */}
    </>
  )
}

export default ItemDetail
