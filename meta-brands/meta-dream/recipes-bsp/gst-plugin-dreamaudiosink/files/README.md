# gst-plugin-dreamaudiosink

GStreamer 1.0 audio sink for Dreambox AMLogic boxes (DreamOne / DreamTwo).

Provides `dreamaudiosink`, a sink element that decodes audio PES / raw PCM, writes to ALSA,
and synchronises against the AMLogic hardware AV-sync engine via `/sys/class/tsync/*`.
Exposes `get-decoder-time` signal and `e2-sync` / `e2-async` properties (drop-in replacement
pattern for `dvbaudiosink` in enigma2 `servicemp3.cpp`).

## Status

Phase 1 skeleton. Plugin loads; render returns OK without doing anything.

## License

GPL-2.0-or-later.
