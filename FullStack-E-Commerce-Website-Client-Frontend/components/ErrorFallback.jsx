function ErrorFallback({ error, resetErrorBoundary }) {
  return (
    <div style={{ padding: '20px', background: 'red', color: 'white',marginTop:'100px' }}>
      <h2>Oops! Something went wrong.</h2>
      <pre>{error.message}</pre>
      <button onClick={resetErrorBoundary}>Try again</button>
    </div>
  );
}

export default ErrorFallback
