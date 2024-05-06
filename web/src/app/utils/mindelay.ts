export const sleep = async (ms: number) => new Promise((resolve) => setTimeout(resolve, ms));

export async function minDelay<T>(promise: Promise<T>, ms: number = 500) {
  let [p] = await Promise.all([promise, sleep(ms)]);

  return p;
}
