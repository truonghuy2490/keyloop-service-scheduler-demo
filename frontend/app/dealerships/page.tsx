import DealershipList from "@/features/dealerships/components/dealership-list";

export default function DealershipsPage() {
  return (
    <main className="min-h-screen bg-base-200 p-6 md:p-10">
      <div className="mx-auto max-w-7xl">
        <DealershipList />
      </div>
    </main>
  );
}