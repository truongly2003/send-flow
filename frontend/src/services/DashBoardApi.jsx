import httpRequest from "@configs/httpRequest";
const dashBoardApi = {
  // user
  getDashBoardUser: async (userId) => {
    try {
      const response = await httpRequest.get(`/dashboard/user/${userId}`);
      return response.data;
    } catch (error) {
      console.error("Error fetching:", error);
      throw error;
    }
  },
  // admin
  getDashBoardAdmin: async () => {
    try {
      const response = await httpRequest.get(`/dashboard/admin`);
      return response.data;
    } catch (error) {
      console.error("Error fetching:", error);
      throw error;
    }
  },
};

export { dashBoardApi };
