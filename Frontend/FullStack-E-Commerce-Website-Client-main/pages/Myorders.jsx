import React, { useEffect, useState } from "react";
import Myordersitem from "../components/Myordersitem";
import { Link, useOutletContext } from "react-router-dom";

function Myorders() {
  const [myOrders, setMyOrders] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const username = localStorage.getItem("username");
  const [setissign, dark, isdark, issign, userlogin] = useOutletContext();

  useEffect(() => {
    const fetchMyOrders = async () => {
      if (!username) {
        console.log("User not logged in");
        setTimeout(() => {
          setIsLoading(false);
        }, 1000);
        return;
      }

      try {
        const userResponse = await fetch(`http://localhost:8080/api/users/${username}`);
        if (!userResponse.ok) {
          console.error("Failed to fetch user details. User not found.");
          return;
        }
        const user = await userResponse.json();

        const ordersResponse = await fetch(
          `http://localhost:8080/api/orders/user/${user.id}`
        );
        if (!ordersResponse.ok) {
          throw new Error("Failed to fetch orders");
        }

        const orders = await ordersResponse.json();
        if (orders && orders.length > 0) {
          setMyOrders(orders);
          console.log(orders);
          
        } else {
          console.log("No orders found for the user");
        }
      } catch (error) {
        console.error("Error fetching orders:", error);
      } finally {
        setTimeout(() => {
          setIsLoading(false);
        }, 1000);
      }
    };

    fetchMyOrders();
  }, [username]);

  return (
    <div className={`order-container`}>
      {isLoading ? (
        <div className="admin">
          <h1>Loading Order Details...</h1>
        </div>
      ) : myOrders.length === 0 ? (
        <div className="admin">
          <h1>No Orders Yet</h1>
        </div>
      ) : (
        <>
          <h1 className="ordhead">My Orders</h1>
          <div className={`order-grid ${dark ? "dark" : ""}`}>
            <div className="grid-header">
              <div className="amp u header-item">Order ID</div>
              <div className="amp us header-item">Order Date</div>
              <div className="amp o header-item">Order Details</div>
              <div className="amp t header-item">Total Price</div>
            </div>
            <div className="grid-body">
              {myOrders.map((order, orderIndex) => (
                <div key={order.id} className="grid-row">
                  <div className="amp grid-item username-column">
                    {order.order_Id}
                  </div>
                  <div className="amp grid-item user-details-column">
                    {new Date(order.orderDate).toLocaleString("en-IN", {
                      day: "numeric",
                      month: "long",
                      year: "numeric",
                      hour: "numeric",
                      minute: "numeric",
                      second: "numeric",
                      hour12: true,
                    })}
                  </div>
                  {/* <div className="grid-item orders-column">
                    {order.orderItems.map((orderItem, itemIndex) => (
                      <Myordersitem
                        key={`${order.id}-${orderItem.product.id}-${itemIndex}`}
                        productId={orderItem.product.id}
                        title={orderItem.product.title}
                        price={orderItem.product.price}
                        quantity={orderItem.quantity}
                        imageUrl={orderItem.product.image}
                        rating={orderItem.product.rating.rate}
                        index={itemIndex}
                      />
                    ))}
                  </div> */}
                  <div className="grid-item orders-column">
  {order.orderItems.map((orderItem, itemIndex) => (
    <div className="ord" key={`${order.id}-${orderItem.product.id}-${itemIndex}`}>
      <Link
       to={`/${orderItem.product.id}`}>
      <span className="userord amp">Product: {orderItem.product.title}</span>
      </Link>
      <span className="amp">Quantity: {orderItem.quantity}</span>
      <span className="amp">Price: ${orderItem.product.price.toFixed(2)}</span>
    </div>
  ))}
</div>
                  <div className="amp grid-item total-price-column">
                    ${order.orderTotal.toFixed(2)}
                  </div>
                </div>
              ))}
            </div>
          </div>
        </>
      )}
    </div>
  );
}

export default Myorders;