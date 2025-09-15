import { createRoot } from 'react-dom/client'
import App from './App'
import { Provider } from 'react-redux'
import { store } from '../store'
import { RouterProvider, createBrowserRouter } from 'react-router-dom'
import Home from '../pages/Home'
import Cart from '../pages/Cart'
import Wish from '../pages/Wish'
import AboutUs from '../components/About'
import ContactUs from '../components/Contact'
import ItemDetail from '../components/ItemDetail'
import ContactForm from '../components/ContactForm'
import Myorders from '../pages/Myorders'
import CarouselPage from '../components/CarouselPage'
import Diwali from '../components/Diwali'
import AdminDashBoard from '../components/AdminDashBoard'
import UpdateProduct from '../components/UpdateProduct'
import AddNewProduct from '../components/AddNewProduct'
import OrderConfirmation from '../components/OrderConfirmation'
import Root from '../pages/Root'
import EditUser from '../components/EditUser'
import EmailsList from '../components/EmailsList'
import FeedbacksList from '../components/FeedbacksList'
import OutOfStockProductsDB from '../components/OutOfStockProducts'
import OrderCancelConfirm from '../components/OrderCancelConfirm'
import SelectProducts from '../components/SelectProducts'
import ErrorFallback from '../components/ErrorFallback'
import { ErrorBoundary } from 'react-error-boundary'

const router = createBrowserRouter([
  {
    path: '/',
    element: <App />,
    errorElement: <ErrorFallback />,
    children: [
      {
        path: '/',
        element: <Root />,
      },
      {
        path: '/cart',
        element: <Cart />,
      },
      {
        path: '/myorder',
        element: <Myorders />,
      },
      {
        path: '/wish',
        element: <Wish />,
      },
      {
        path: '/about',
        element: <AboutUs />,
      },
      {
        path: '/diwali',
        element: <Diwali />,
      },
      {
        path: '/contact',
        element: <ContactUs />,
        children: [
          {
            path: 'feedback', // Nested route for /contact/form
            element: <ContactForm />,
          },
        ],
      },
      {
        path: '/product/:productId',
        element: <ItemDetail />,
      },

      {
        path: '/carousel/:carousel',
        element: <CarouselPage />,
      },
      {
        path: '/search/:searchTerm',
        element: <CarouselPage />,
      },
      {
        path: '/Admin',
        element: <AdminDashBoard />,
      },
      { path: '/update-product/:id', element: <UpdateProduct /> },
      {
        path: '/Add',
        element: <AddNewProduct />,
      },
      {
        path: '/OrderConfirmation',
        element: <OrderConfirmation />,
      },
      {
        path: '/Home',
        element: <Home />,
      },
      {
        path: '/EditUser',
        element: <EditUser />,
      },
      {
        path: '/Emailslist',
        element: <EmailsList />,
      },
      {
        path: '/FeedbacksList',
        element: <FeedbacksList />,
      },
      {
        path: '/OutOfStockProducts',
        element: <OutOfStockProductsDB />,
      },
      {
        path: '/OrderCancelConfirm',
        element: <OrderCancelConfirm />,
      },
      {
        path: '/top',
        element: <SelectProducts />,
      },
      ,
      {
        path: '*',
        element: <Home />,
      },
    ],
  },
])
createRoot(document.querySelector('#root')).render(
  <Provider store={store}>
    <ErrorBoundary FallbackComponent={ErrorFallback}>
      <RouterProvider router={router} />
    </ErrorBoundary>
  </Provider>
);
