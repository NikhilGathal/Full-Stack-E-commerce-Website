import c from '../assets/3.jpg'
import g from '../assets/7.jpg'
import t from '../assets/20.jpg'
import React, { useEffect, useState } from 'react';
import { FaStar } from 'react-icons/fa';
import { Link, useOutletContext } from 'react-router-dom';
import './TopProducts.css';

const TopProducts = ({ handleOrderPopup, id }) => {
  const fallbackProducts = [
  { id: 3, image: c, title: 'Mens Cotton Jacket', price: '55.99' },
  { id: 7, image: g, title: 'White Gold Plated Princess', price: '9.99' },
  { id: 20, image: t, title: 'DANVOUY Womens T Shirt Casual', price: '12.99' }
];
  const [, dark] = useOutletContext();
  const [products, setProducts] = useState(fallbackProducts);



useEffect(() => {
  const fetchTopProducts = async () => {
    const idsRes = await fetch('http://localhost:8080/api/top-products');
    const topIds = await idsRes.json();

    if (topIds.length > 0) {
      const details = await Promise.all(
        topIds.map((tp) =>
          fetch(`http://localhost:8080/api/products/${tp.productId}`).then((res) => res.json())
        )
      );
      setProducts(details);
    }
  };

  fetchTopProducts();
}, []);

  return (
    <div id={id} className={`tp ${dark ? 'dark' : ''}`}>
      <div className="container">
        <div className="text-left mb-24">
          <p className="text-sm text-primary">Top Rated Products for you</p>
          <h1 className="text-3xl font-bold">Best Products</h1>
        </div>

        <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-20 md:gap-5 place-items-center">
          {products.map((data) => (
            <div className="product-card" key={data.id}>
              <div className="image-section">
                <Link to={`/product/${data.id}`}>
                  <img src={data.image} alt={data.title} className="image" />
                </Link>
              </div>
              <div className="details-section">
                <div className="rating">
                  <FaStar />
                  <FaStar />
                  <FaStar />
                  <FaStar />
                </div>
                <Link to={`/${data.id}`}>
                  <button onClick={handleOrderPopup}>
                    Order Now
                  </button>
                </Link>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
};

export default TopProducts;
