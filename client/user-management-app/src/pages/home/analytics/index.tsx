import { ChartAreaInteractive } from "@/components/chart-area-interactive";
import { CircleAlertIcon } from "lucide-react";

const Analytics = () => {
  return (
    <>
      <p className="mb-4 ml-2 flex items-center gap-2">
        <CircleAlertIcon className="text-muted-foreground" size={16} />
        Just a readonly page, nothing special here.
      </p>
      <ChartAreaInteractive />
    </>
  );
};

export default Analytics;
