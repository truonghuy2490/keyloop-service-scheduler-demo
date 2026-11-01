export const API_ENDPOINTS = {
  APPOINTMENTS: {
    BASE: "/appointments",
    BY_ID: (id: string) => `/appointments/${id}`,
  },

  DEALERSHIPS: {
    BASE: "/dealerships",
    BY_ID: (id: string) => `/dealerships/${id}`,
  },

  VEHICLES: {
    BASE: "/vehicles",
    BY_ID: (id: string) => `/vehicles/${id}`,
  },

  SERVICE_TYPES: {
    BASE: "/service-types",
    BY_ID: (id: string) => `/service-types/${id}`,
  },
} as const;