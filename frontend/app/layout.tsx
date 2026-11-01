
import type { Metadata } from "next";
import "./globals.css";
import ReduxProvider from "@/lib/redux/providers";

export const metadata: Metadata = {
  title: {
    default: "Service Scheduler | Keyloop",
    template: "%s | Service Scheduler",
  },
  description:
    "Automotive Service Appointment Scheduling Platform",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en" data-theme="keyloop">
      <body>
        <ReduxProvider>{children}</ReduxProvider>
      </body>
    </html>
  );
}
