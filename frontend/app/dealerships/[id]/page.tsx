import Link from "next/link";
import { ArrowLeft } from "lucide-react";
import DealershipDetails from "@/features/dealerships/components/dealership-details";

interface DealershipDetailsPageProps {
  params: Promise<{
    id: string;
  }>;
}

export default async function DealershipDetailsPage({
  params,
}: DealershipDetailsPageProps) {
  const { id } = await params;

  return (
    <main className="min-h-screen bg-base-200 p-6 md:p-10">
      <div className="mx-auto max-w-7xl space-y-6">
        <Link
          href="/dealerships"
          className="btn btn-ghost btn-sm"
        >
          <ArrowLeft size={16} />
          Back to Dealerships
        </Link>

        <DealershipDetails dealershipId={id} />
      </div>
    </main>
  );
}
