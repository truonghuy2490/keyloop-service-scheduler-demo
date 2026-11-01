export interface Dealership {
  id: string;
  name: string | null;
  address: string | null;
  phone?: string | null;
  email?: string | null;
}

// DayOfWeek from Java: java.time.DayOfWeek
export type DayOfWeek =
  | "MONDAY"
  | "TUESDAY"
  | "WEDNESDAY"
  | "THURSDAY"
  | "FRIDAY"
  | "SATURDAY"
  | "SUNDAY";

export interface DealershipWorkingHours {
  dayOfWeek: DayOfWeek;
  isClosed: boolean;
  opensAt: string | null;
  closesAt: string | null;
}

export interface DealershipDateOverride {
  businessDate: string;
  isClosed: boolean;
  opensAt: string | null;
  closesAt: string | null;
  reason: string | null;
}

export interface DealershipResourceSummary {
  serviceBayCount: number;
  technicianCount: number;
  serviceCount: number;
}

export interface DealershipDetails {
  id: string;
  name: string;
  timeZone: string;
  workingHours: DealershipWorkingHours[];
  dateOverrides: DealershipDateOverride[];
  resourceSummary: DealershipResourceSummary;
}
