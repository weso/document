const repoUrl = "https://github.com/weso/document";

const apiUrl = "/document/api/es/weso/index.html";

// See https://docusaurus.io/docs/site-config for available options.
const siteConfig = {
  title: "Document (library)",
  tagline: "Document",
  url: "https://weso.github.io/document",
  baseUrl: "/document/",

  customDocsPath: "document-docs/target/mdoc",

  projectName: "document",
  organizationName: "weso",

  headerLinks: [
    { href: apiUrl, label: "API Docs" },
    { doc: "overview", label: "Documentation" },
    { href: repoUrl, label: "GitHub" }
  ],

  headerIcon: "img/logo-weso.png",
  titleIcon: "img/logo-weso.png",
  favicon: "img/favicon/favicon.ico",

  colors: {
    primaryColor: "#122932",
    secondaryColor: "#153243"
  },

  copyright: `Copyright © 2019-${new Date().getFullYear()} WESO Research group.`,

  highlight: { theme: "github" },

  onPageNav: "separate",

  separateCss: ["api"],

  cleanUrl: true,

  repoUrl,

  apiUrl
};

module.exports = siteConfig;
