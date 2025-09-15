// import { useEffect, useState } from 'react'
// import { useNavigate, useOutletContext } from 'react-router-dom'
// import './SelectProducts.css'

// const SelectProducts = () => {
//   const navigate = useNavigate()
//   const [ids, setIds] = useState('')
//   const [, dark] = useOutletContext() // ✅ get dark from context

//   const handleSave = () => {
//     console.log(ids);
    
//     const arr = ids
//       .split(',')
//       .map((id) => parseInt(id.trim(), 10))
//       .filter((id) => !isNaN(id))

//     if (arr.length !== 3) {
//       alert('Please enter all 3 IDs!')
//       return
//     }
//     localStorage.setItem('topProductList', JSON.stringify(arr))
//     alert('Top products saved!')
//     navigate('/')
//   }

//   const isAdminLog = localStorage.getItem('isadminlog') === 'true'
//   useEffect(() => {
//     const alrd = JSON.parse(localStorage.getItem('topProductList'))
//   if (alrd && Array.isArray(alrd)) {
//     setIds(alrd.join(','))   // convert array [9,5,8] → "9,5,8"
//   }

//     if (!isAdminLog) {
//       navigate('/')
//     }
//   }, [isAdminLog])

//   if (!isAdminLog) {
//     return null // ✅ Prevents rendering if admin is not logged in
//   }

//   return (
//     <div className="select-products-wrapper">
//       <h2 className="select-products-title">Select Top Products ID</h2>
//       <div className={`select-products-container ${dark ? 'dark' : ''}`}>
//         <input
//           type="text"
//           placeholder="Enter product IDs, e.g. 3,7,20"
//           value={ids}
//           onChange={(e) => setIds(e.target.value)}
//           className="select-products-input"
//         />
//         <button onClick={handleSave} className="select-products-button">
//           Save
//         </button>
//       </div>
//     </div>
//   )
// }

// export default SelectProducts


import { useEffect, useState } from 'react';
import { useNavigate, useOutletContext } from 'react-router-dom';
import './SelectProducts.css';

const SelectProducts = () => {
  const navigate = useNavigate();
  const [ids, setIds] = useState('');
  const [, dark] = useOutletContext();

const handleSave = async () => {
  const arr = ids
    .split(',')
    .map((id) => parseInt(id.trim(), 10))
    .filter((id) => !isNaN(id));

  if (arr.length !== 3) {
    alert('Please enter all 3 IDs!');
    return;
  }

  try {
    const res = await fetch('http://localhost:8080/api/top-products', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(arr),
    });

    if (!res.ok) {
      // server responded with 4xx or 5xx
      const errorMsg = await res.text();
      throw new Error(`Failed to save: ${errorMsg}`);
    }

    alert('Top products saved!');
    navigate('/');
  } catch (err) {
    console.error('Error saving top products:', err);
    alert(`Something went wrong: ${err.message}`);
  }
};


useEffect(() => {
  const fetchTopProducts = async () => {
    try {
      const res = await fetch('http://localhost:8080/api/top-products');

      if (!res.ok) {
        throw new Error(`Failed to fetch: ${res.status} ${res.statusText}`);
      }

      const data = await res.json();
      if (data && Array.isArray(data) && data.length > 0) {
        setIds(data.map((tp) => tp.productId).join(','));
      }
    } catch (err) {
      console.error('Error fetching top products:', err);
      alert('Could not load top products. Please try again later.');
    }
  };

  fetchTopProducts();
}, []);


  return (
    <div className="select-products-wrapper">
      <h2 className="select-products-title">Select Top Products ID</h2>
      <div className={`select-products-container ${dark ? 'dark' : ''}`}>
        <input
          type="text"
          placeholder="Enter product IDs, e.g. 3,7,20"
          value={ids}
          onChange={(e) => setIds(e.target.value)}
          className="select-products-input"
        />
        <button onClick={handleSave} className="select-products-button">
          Save
        </button>
      </div>
    </div>
  );
};

export default SelectProducts;
