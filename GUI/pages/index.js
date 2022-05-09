import React, { useEffect } from "react";
import { useRouter } from "next/router";

const app = () => {
  const router = useRouter();
  //need to check if login need to be
  useEffect(() => {
    router.push("/login");
  }, []);
  return null;
};

export default app;
