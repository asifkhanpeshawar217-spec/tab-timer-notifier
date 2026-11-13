# Tab Timer Notifier

A client-only Fabric mod for Minecraft Java 1.21.11. It reads the server-sent player-list (Tab) footer even while the Tab screen is closed, looks for `Nuke: 11m 59s` and `Clone: 3m 27s`, estimates each cooldown locally, and plays a sound plus an action-bar notification when a cooldown becomes ready.

## Important compatibility note

This mod only works if the server continues sending updated player-list header/footer packets to your client while the Tab screen is closed. Minecraft normally receives this packet independently of whether the player-list screen is open, but some servers may only refresh it periodically or may stop sending updates. If the server does not send a later `Ready!` state or a zero timer, the local countdown estimate is used.

The parser currently expects footer lines in these forms:

- `Nuke: 11m 59s`
- `Clone: 3m 27s`
- `Nuke: Ready!`
- `Clone: Ready!`

## Build the mod

Requirements: Java 21 and IntelliJ IDEA (or another IDE with Gradle support).

1. Extract this project folder.
2. Open the `TabTimerNotifier` folder in IntelliJ IDEA and choose **Open as Project**.
3. Allow Gradle to sync and download the Minecraft/Fabric dependencies.
4. Run the Gradle task **Tasks → build → build**.
5. The remapped mod JAR will be created in `build/libs/` (the file without `-dev` in its name).
6. Put that JAR in your Minecraft instance's `mods` folder alongside Fabric API.

You need Fabric Loader 0.18.1 or newer, Fabric API for 1.21.11, and Java 21.

## Troubleshooting

- If it does not detect the timers, check that the server's footer text matches the formats above.
- If it detects the timers but the alert is late, the server may update its footer only occasionally; the mod can only react to information the server sends.
- Sound volume follows Minecraft's player sound settings.


## Sound settings

Press **N** (when no other screen is open) to open the sound settings screen. Choose separate sounds for Nuke and Clone, preview them, adjust their independent volume sliders, or use **Restore Default** for either alert. Sound choices and volumes are saved in `config/tab-timer-notifier.json` and remain saved after restarting Minecraft.

Custom MP3 playback uses a bundled Java Sound MP3 decoder and a separate desktop audio output, so Minecraft's in-game master/music/sound sliders do not control these alerts. The operating system's output device and per-application volume controls can still affect the Minecraft Java process. WAV and AIFF files are also accepted.
