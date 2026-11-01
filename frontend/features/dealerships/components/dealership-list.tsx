"use client";

import { useMemo, useState } from "react";
import SearchInput from "@/shared/components/search";
import Pagination from "@/shared/components/pagination";
import { useGetDealershipsQuery } from "@/features/dealerships/dealership.api";
import DealershipCard from "./dealership-card";

export default function DealershipList() {
  const [pageNumber, setPageNumber] = useState(1);
  const [keyword, setKeyword] = useState("");

  const pageSize = 5;

  const { data, error, isLoading, isFetching, refetch } =
    useGetDealershipsQuery({ pageNumber, pageSize });

  const filteredDealerships = useMemo(() => {
    const normalizedKeyword = keyword.trim().toLowerCase();

    if (!normalizedKeyword) {
      return data?.items ?? [];
    }

    return (data?.items ?? []).filter((dealership) => {
      const searchableFields = [
        dealership.name,
        dealership.address,
        dealership.phone,
        dealership.email,
      ];

      return searchableFields.some((field) =>
        field?.toLowerCase().includes(normalizedKeyword)
      );
    });
  }, [data?.items, keyword]);

  const handleSearch = (value: string) => {
    setKeyword(value);
    setPageNumber(1);
  };

  if (isLoading) {
    return (
      <div className="flex justify-center py-16">
        <span className="loading loading-spinner loading-lg" />
      </div>
    );
  }

  if (error) {
    return (
      <div className="alert alert-error">
        <span>Failed to load dealerships.</span>
        <button className="btn btn-sm" onClick={() => refetch()}>
          Retry
        </button>
      </div>
    );
  }

  return (
    <section className="space-y-6">
      {/* Header */}
      <div className="flex flex-col justify-between gap-4 sm:flex-row sm:items-center">
        <div>
          <h1 className="text-2xl font-bold">Dealerships</h1>
          <p className="text-sm text-base-content/60">
            Explore and manage dealership locations.
          </p>
        </div>

        <SearchInput
          value={keyword}
          onChange={handleSearch}
          placeholder="Search dealerships..."
        />
      </div>

      {/* Result count */}
      <div className="flex items-center justify-between">
        <span className="text-sm text-base-content/60">
          {keyword.trim()
            ? `${filteredDealerships.length} search results`
            : `${data?.totalCount ?? 0} dealerships`}
        </span>

        {isFetching && (
          <span className="loading loading-spinner loading-sm" />
        )}
      </div>

      {/* Dealership list */}
      {filteredDealerships.length > 0 ? (
        <div className="grid grid-cols-1 gap-5 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4">
          {filteredDealerships.map((dealership) => (
            <DealershipCard
              key={dealership.id}
              dealership={dealership}
            />
          ))}
        </div>
      ) : (
        <div className="rounded-xl border border-dashed border-base-300 py-16 text-center">
          <h3 className="font-semibold">
            {keyword.trim()
              ? "No matching dealerships"
              : "No dealerships found"}
          </h3>

          <p className="mt-1 text-sm text-base-content/60">
            {keyword.trim()
              ? "Try searching with a different keyword."
              : "There are no dealerships available."}
          </p>
        </div>
      )}

      {/* Pagination */}
      {!keyword.trim() && data && (
        <Pagination
          pageNumber={data.pageNumber}
          totalPages={data.totalPages}
          pageSize={data.pageSize}
          totalCount={data.totalCount}
          hasPreviousPage={data.pageNumber > 1}
          hasNextPage={data.pageNumber < data.totalPages}
          onPageChange={setPageNumber}
        />
      )}
    </section>
  );
}
