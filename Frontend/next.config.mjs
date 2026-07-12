/** @type {import('next').NextConfig} */
const nextConfig = {
  /* config options here */
  /**Allows next.js to make requests to springboot during server component data fetching */
  experimental: {
    serverActions: true,
  },
};

module.exports = nextConfig;
