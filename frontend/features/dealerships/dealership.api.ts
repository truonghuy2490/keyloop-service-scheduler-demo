import { baseApiSlice } from "@/lib/api/base-api";
import { API_ENDPOINTS } from "@/lib/api/endpoints";
import {
  PagedList,
  PaginationRequest,
} from "@/lib/api/base.types";

import type { Dealership } from "./dealership.types";

export const dealershipApi = baseApiSlice.injectEndpoints({
  endpoints: (builder) => ({
    getDealerships: builder.query<
      PagedList<Dealership>,
      PaginationRequest
    >({
      query: (params) => ({
        url: API_ENDPOINTS.DEALERSHIPS.BASE,
        method: "GET",
        params,
      }),

      providesTags: ["Dealership"],
    }),
  }),
});

export const {
  useGetDealershipsQuery,
} = dealershipApi;