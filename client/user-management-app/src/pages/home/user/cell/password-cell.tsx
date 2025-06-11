import { EyeIcon, EyeOffIcon } from "lucide-react";
import React from "react";

type PasswordCellProps = { value: string };

const PasswordCell = ({ value }: PasswordCellProps) => {
  const [visible, setVisible] = React.useState(false);
  const masked = "•".repeat(8);

  return (
    <div className="flex items-center space-x-2">
      <button
        type="button"
        onClick={() => setVisible((prev) => !prev)}
        className="h-5 w-5 p-0 rounded hover:bg-muted/50"
        aria-label={visible ? "Hide password" : "Show password"}
      >
        {visible ? (
          <EyeOffIcon className="h-4 w-4 text-muted-foreground" />
        ) : (
          <EyeIcon className="h-4 w-4 text-muted-foreground" />
        )}
      </button>
      <span className="font-mono text-sm leading-tight">{visible ? value : masked}</span>
    </div>
  );
};

export default PasswordCell;
