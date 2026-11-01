
"use client";

import { useMemo, useState } from "react";
import { ChevronLeft, ChevronRight, CalendarDays } from "lucide-react";

import type {
  DayOfWeek,
  DealershipWorkingHours,
  DealershipDateOverride,
} from "@/features/dealerships/dealership.types";

interface DealershipMonthCalendarProps {
  workingHours: DealershipWorkingHours[];
  dateOverrides: DealershipDateOverride[];
  timeZone: string;
}

type MonthValue = {
  year: number;
  month: number;
};

type DayStatus = "open" | "closed" | "holiday" | "special";

interface CalendarDay {
  date: number;
  dateKey: string;
  status: DayStatus;
  label: string;
  hours?: string;
  isToday: boolean;
}

const WEEKDAYS: {
  key: DayOfWeek;
  label: string;
}[] = [
  { key: "SUNDAY", label: "Sun" },
  { key: "MONDAY", label: "Mon" },
  { key: "TUESDAY", label: "Tue" },
  { key: "WEDNESDAY", label: "Wed" },
  { key: "THURSDAY", label: "Thu" },
  { key: "FRIDAY", label: "Fri" },
  { key: "SATURDAY", label: "Sat" },
];

const JS_DAY_TO_WEEKDAY: DayOfWeek[] = [
  "SUNDAY",
  "MONDAY",
  "TUESDAY",
  "WEDNESDAY",
  "THURSDAY",
  "FRIDAY",
  "SATURDAY",
];

const STATUS_STYLES: Record<DayStatus, string> = {
  open: "border-secondary/50 bg-secondary/15 text-base-content",
  closed: "border-base-300 bg-base-200 text-base-content/50",
  holiday: "border-warning/40 bg-warning/10 text-base-content",
  special: "border-accent/50 bg-accent/10 text-primary",
};

function pad(value: number): string {
  return String(value).padStart(2, "0");
}

function toDateKey(year: number, month: number, day: number): string {
  return `${year}-${pad(month + 1)}-${pad(day)}`;
}

function getTodayInTimeZone(timeZone: string): string {
  const parts = new Intl.DateTimeFormat("en-CA", {
    timeZone,
    year: "numeric",
    month: "2-digit",
    day: "2-digit",
  }).formatToParts(new Date());

  const getPart = (type: string) =>
    parts.find((part) => part.type === type)?.value ?? "";

  return `${getPart("year")}-${getPart("month")}-${getPart("day")}`;
}

function formatTime(time: string | null): string {
  return time ? time.slice(0, 5) : "";
}

function getMonthTitle({ year, month }: MonthValue): string {
  return new Intl.DateTimeFormat("en-US", {
    month: "long",
    year: "numeric",
  }).format(new Date(year, month, 1));
}

export default function DealershipMonthCalendar({
  workingHours,
  dateOverrides,
  timeZone,
}: DealershipMonthCalendarProps) {
  const todayKey = getTodayInTimeZone(timeZone);

  const [selectedMonth, setSelectedMonth] =
    useState<MonthValue | null>(null);

  const currentMonth = useMemo(() => {
    if (selectedMonth) return selectedMonth;

    const [year, month] = todayKey.split("-").map(Number);

    return {
      year,
      month: month - 1,
    };
  }, [selectedMonth, todayKey]);

  const calendarDays = useMemo(() => {
    const { year, month } = currentMonth;

    const firstDayOfWeek = new Date(year, month, 1).getDay();
    const daysInMonth = new Date(year, month + 1, 0).getDate();

    const workingHoursMap = new Map(
      workingHours.map((item) => [item.dayOfWeek, item]),
    );

    const overrideMap = new Map(
      dateOverrides.map((item) => [item.businessDate, item]),
    );

    const days: (CalendarDay | null)[] = [];

    // Empty cells before the first day of the month.
    for (let i = 0; i < firstDayOfWeek; i++) {
      days.push(null);
    }

    for (let day = 1; day <= daysInMonth; day++) {
      const dateKey = toDateKey(year, month, day);
      const date = new Date(year, month, day);
      const weekday = JS_DAY_TO_WEEKDAY[date.getDay()];

      const weeklySchedule = workingHoursMap.get(weekday);
      const override = overrideMap.get(dateKey);

      let status: DayStatus = "closed";
      let label = "No schedule";
      let hours: string | undefined;

      // Date override has higher priority than weekly schedule.
      if (override) {
        if (override.isClosed) {
          status = "holiday";
          label = override.reason || "Holiday";
        } else {
          status = "special";
          label = "Special hours";

          if (override.opensAt && override.closesAt) {
            hours = `${formatTime(override.opensAt)} - ${formatTime(
              override.closesAt,
            )}`;
          }
        }
      } else if (weeklySchedule) {
        if (weeklySchedule.isClosed) {
          status = "closed";
          label = "Closed";
        } else {
          status = "open";
          label = "Open";

          if (weeklySchedule.opensAt && weeklySchedule.closesAt) {
            hours = `${formatTime(
              weeklySchedule.opensAt,
            )} - ${formatTime(weeklySchedule.closesAt)}`;
          }
        }
      }

      days.push({
        date: day,
        dateKey,
        status,
        label,
        hours,
        isToday: dateKey === todayKey,
      });
    }

    return days;
  }, [currentMonth, workingHours, dateOverrides, todayKey]);

  function changeMonth(offset: number) {
    setSelectedMonth((previous) => {
      const base = previous ?? {
        year: Number(todayKey.slice(0, 4)),
        month: Number(todayKey.slice(5, 7)) - 1,
      };

      const next = new Date(
        base.year,
        base.month + offset,
        1,
      );

      return {
        year: next.getFullYear(),
        month: next.getMonth(),
      };
    });
  }

  const canGoToPreviousMonth = useMemo(() => {
    const todayYear = Number(todayKey.slice(0, 4));
    const todayMonth = Number(todayKey.slice(5, 7)) - 1;

    return (
      currentMonth.year > todayYear ||
      (currentMonth.year === todayYear &&
        currentMonth.month > todayMonth)
    );
  }, [currentMonth, todayKey]);

  return (
    <section className="overflow-hidden rounded-xl border border-base-300 bg-base-100 shadow-sm">
      {/* Header */}
      <div className="border-b border-base-300 p-5">
        <div className="flex items-center gap-3">
          <div className="flex h-11 w-11 items-center justify-center rounded-xl bg-primary/10 text-primary">
            <CalendarDays size={22} />
          </div>

          <div>
            <h2 className="font-semibold text-base-content">
              Monthly Business Calendar
            </h2>
            <p className="text-sm text-base-content/60">
              Opening days, regular closures and special dates
            </p>
          </div>
        </div>
      </div>

      <div className="space-y-5 p-5">
        {/* Month navigation */}
        <div className="flex items-center justify-between gap-3">
          <button
            type="button"
            className="btn btn-square btn-ghost text-primary hover:bg-primary/10"
            onClick={() => changeMonth(-1)}
            disabled={!canGoToPreviousMonth}
            aria-label="Previous month"
          >
            <ChevronLeft size={22} />
          </button>

          <h3 className="text-lg font-bold text-base-content">
            {getMonthTitle(currentMonth)}
          </h3>

          <button
            type="button"
            className="btn btn-square btn-primary"
            onClick={() => changeMonth(1)}
            aria-label="Next month"
          >
            <ChevronRight size={22} />
          </button>
        </div>

        {/* Calendar Grid */}
        <div className="grid grid-cols-7 gap-1.5 sm:gap-2">
          {/* Weekday headings */}
          {WEEKDAYS.map((weekday) => (
            <div
              key={weekday.key}
              className="py-2 text-center text-xs font-semibold text-base-content/60 sm:text-sm"
            >
              {weekday.label}
            </div>
          ))}

          {/* Calendar cells */}
          {calendarDays.map((day, index) => {
            if (!day) {
              return (
                <div
                  key={`empty-${index}`}
                  className="min-h-20 rounded-lg sm:min-h-24"
                />
              );
            }

            return (
              <div
                key={day.dateKey}
                className={`relative flex min-h-20 flex-col items-center justify-center gap-1 rounded-lg border p-1 text-center transition-colors sm:min-h-24 sm:p-2 ${
                  STATUS_STYLES[day.status]
                } ${
                  day.isToday
                    ? "ring-2 ring-primary ring-offset-2 ring-offset-base-100"
                    : ""
                }`}
                title={`${day.dateKey}: ${day.label}${
                  day.hours ? ` (${day.hours})` : ""
                }`}
              >
                <span className="text-sm font-bold sm:text-base">
                  {day.date}
                </span>

                {day.isToday && (
                  <span className="text-[10px] font-bold text-primary">
                    Today
                  </span>
                )}

                <span className="line-clamp-2 text-[9px] leading-tight sm:text-xs">
                  {day.status === "closed"
                    ? day.label
                    : day.status === "holiday"
                      ? "Holiday"
                      : day.status === "special"
                        ? "Special"
                        : day.hours || "Open"}
                </span>
              </div>
            );
          })}
        </div>

        {/* Legend */}
        <div className="space-y-3 border-t border-base-300 pt-5">
          <h3 className="text-sm font-semibold text-base-content">
            Calendar legend
          </h3>

          <div className="grid gap-3 sm:grid-cols-2">
            <div className="flex items-center gap-3 text-sm">
              <span className="h-4 w-4 shrink-0 rounded border border-secondary/50 bg-secondary/15" />
              <span className="text-base-content/70">
                Regular working day
              </span>
            </div>

            <div className="flex items-center gap-3 text-sm">
              <span className="h-4 w-4 shrink-0 rounded border border-base-300 bg-base-200" />
              <span className="text-base-content/70">
                Regular day off
              </span>
            </div>

            <div className="flex items-center gap-3 text-sm">
              <span className="h-4 w-4 shrink-0 rounded border border-warning/40 bg-warning/10" />
              <span className="text-base-content/70">
                Holiday / Special closure
              </span>
            </div>

            <div className="flex items-center gap-3 text-sm">
              <span className="h-4 w-4 shrink-0 rounded border border-accent/50 bg-accent/10" />
              <span className="text-base-content/70">
                Special opening hours
              </span>
            </div>

            <div className="flex items-center gap-3 text-sm">
              <span className="h-4 w-4 shrink-0 rounded border-2 border-primary bg-base-100" />
              <span className="text-base-content/70">
                Today
              </span>
            </div>
          </div>
        </div>
      </div>
    </section>
  );
}
