module.exports = {
  mount: {
    public: "/",
    src: "/_dist_",
  },
  plugins: ["@snowpack/plugin-vue", "@snowpack/plugin-dotenv"],
  devOptions: {
    port: 3000,
  },
};
