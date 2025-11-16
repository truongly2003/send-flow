import { Navigate } from "react-router-dom";
import PropTypes from "prop-types";
import { useAuth } from "../hooks/useAuth";
// route user login
export function ProtectedRoute({ children }) {
    const currentUser = useAuth();

  if (!currentUser) return <Navigate to="/login" replace />;
  return children;
}
ProtectedRoute.propTypes = {
  children: PropTypes.node,
};
// route admin
export function AdminRoute({ children }) {
  const currentUser = useAuth();
  if (!currentUser) return <Navigate to="/login" replace />;
  if (currentUser.role !== "ADMIN") return <Navigate to="/dashboard" replace />;
  return children;
}
AdminRoute.propTypes = {
  children: PropTypes.node,
};
// route client
export function UserRoute({ children }) {
  const currentUser = useAuth();
  if (!currentUser) return <Navigate to="/login" replace />;
  if (currentUser.role !== "USER") return <Navigate to="/admin/dashboard" replace />;
  return children;
}
UserRoute.propTypes = {
  children: PropTypes.node,
};
