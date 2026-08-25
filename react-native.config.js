// This is a brownfield integration: the Android project lives at the repo root
// (there is no separate "android/" folder wrapping a fresh RN app), so the CLI
// needs to be told explicitly where to find it.
module.exports = {
  project: {
    android: {
      sourceDir: '.',
    },
  },
};
