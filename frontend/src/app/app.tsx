import PublicRoutes from '@/core/routes/PublicRoutes';
import PrivateRoutes from '@/core/routes/PrivateRoutes';

function App() {
  return (
    <>
      <PublicRoutes />
      <PrivateRoutes />
    </>
  );
}

export default App;
