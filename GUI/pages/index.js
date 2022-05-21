import { useEffect } from "react";
import { useRouter } from "next/router";

const app = () => {
  const router = useRouter();
  useEffect(() => {
    router.push("/login");
  }, []);
  return null;
};

export default app;
