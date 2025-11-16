import httpRequest from "@configs/httpRequest";
const transactionApi = {
  getAllTransaction: async (page, size) => {
    try {
      const response = await httpRequest.get(
        `/transaction?page=${page}&size=${size}`
      );
      return response.data;
    } catch (error) {
      console.log(error);
    }
  },
};
export { transactionApi };
