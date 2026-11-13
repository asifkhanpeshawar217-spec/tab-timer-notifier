# Build the JAR free with GitHub Actions

1. Create a free account at https://github.com/ if you do not already have one.
2. Create a new **public** repository (for example `tab-timer-notifier`).
3. Extract this ZIP on your computer.
4. Open the extracted `TabTimerNotifier` folder. Upload its contents (including `.github`) to the repository's main branch. Make sure `.github/workflows/build.yml` is present in the repository.
5. Open the repository's **Actions** tab. If prompted, enable workflows.
6. Select **Build Fabric mod** and click **Run workflow** (or wait for the first push build).
7. When the run finishes successfully, open it and download the `tab-timer-notifier` artifact. Extract it to get the `.jar`.

GitHub Actions has free usage for public repositories. Keep the repository public to avoid consuming private-repository Actions minutes.

Note: a successful build only proves compilation. The mod still needs an in-game test on your server, especially to verify that the server sends footer updates while Tab is closed.
