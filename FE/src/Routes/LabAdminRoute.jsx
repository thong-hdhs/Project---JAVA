import { useSelector } from "react-redux";
import { Navigate, Outlet } from "react-router-dom";

const LabAdminRoute = () => {
  const { isAuth, user } = useSelector((state) => state.auth);

  if (!isAuth) {
    return <Navigate to="/login" replace />;
  }

  if (user?.role !== "LAB_ADMIN") {
    return <Navigate to="/403" replace />;
  }

  return <Outlet />;
};

export default LabAdminRoute;
