import type { Metadata } from "next";
import "./globals.css";
import ReduxProvider from "@/lib/redux/providers";

export const metadata: Metadata = {
  title: "Service Scheduler",
  description: "Automotive Service Appointment Scheduler",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body>
        <ReduxProvider>{children}</ReduxProvider>
      </body>
    </html>
  );
}