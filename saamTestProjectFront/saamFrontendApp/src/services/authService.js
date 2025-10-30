import api from "./api";

export const authService = {
  async login(username, password) {
    const response = await api.post("/login", { username, password });
    const token = response.data.token || response.data.jwtValue;
    if (token) {
      localStorage.setItem("token", token);
    }
    return response.data;
  },

  async register(userData) {
    const response = await api.post("/register", userData);
    return response.data;
  },

  logout() {
    localStorage.removeItem("token");
  },

  isAuthenticated() {
    return !!localStorage.getItem("token");
  },
};
