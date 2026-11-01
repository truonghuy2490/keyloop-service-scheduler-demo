"use client";

import { MapPin, Phone, Mail, ArrowRight } from "lucide-react";
import type { Dealership } from "@/features/dealerships/dealership.types";

interface DealershipCardProps {
  dealership: Dealership;
}

export default function DealershipCard({
  dealership,
}: DealershipCardProps) {
  const name = dealership.name?.trim() || "Unnamed Dealership";
  const address = dealership.address?.trim() || "Address not available";
  const phone = dealership.phone?.trim() || "No phone number";
  const email = dealership.email?.trim() || "No email available";

  return (
    <div className="card border border-base-300 bg-base-100 shadow-sm transition-all hover:-translate-y-1 hover:shadow-lg">
      <div className="card-body gap-3">
        <div className="flex items-start justify-between">
          <div className="rounded-lg bg-primary/10 p-3 text-primary">
            <MapPin size={24} />
          </div>

          <span className="badge badge-success badge-outline">
            Active
          </span>
        </div>

        <h2 className="card-title text-lg">
          {name}
        </h2>

        <div className="space-y-2 text-sm text-base-content/70">
          <p className="flex items-start gap-2">
            <MapPin size={16} className="mt-0.5 shrink-0" />
            <span>{address}</span>
          </p>

          <p className="flex items-center gap-2">
            <Phone size={16} className="shrink-0" />
            <span>{phone}</span>
          </p>

          <p className="flex items-center gap-2 min-w-0">
            <Mail size={16} className="shrink-0" />
            <span className="truncate">{email}</span>
          </p>
        </div>

        <div className="card-actions mt-2">
          <button className="btn btn-outline btn-primary btn-sm w-full">
            View details
            <ArrowRight size={16} />
          </button>
        </div>
      </div>
    </div>
  );
}