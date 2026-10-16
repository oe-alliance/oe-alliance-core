Dreambox One/Two source-built video sink
=======================================

Purpose
-------
Replacement implementation of the normal GStreamer element "dreamvideosink".
It is not an additional E2-only sink and does not require renaming the factory
in Enigma2. The intended scope includes original codecs and the application
overlay interface, even when current OpenATV Enigma2 does not consume them.

This is an INTERNAL candidate undergoing Dream Two hardware validation, not a
declaration of release equivalence. See docs/CODECS.txt for implemented paths and remaining
parity work, docs/OVERLAYS.txt for the compositor API, and TESTING.txt for the
hardware gate. Static ABI recovery and mock tests cannot establish decoder
acceptance, picture quality or A/V synchronization on a Dreambox.

Architecture
------------
gstdreamvideosink.c: GStreamer state, negotiation, bounded queue, decoder worker,
                     events, metadata and public action signals.
dream_video_device.c: Linux DVB/Dream ioctl ABI and nonblocking device handling.
dream_codec.c:        Bounded codec configuration and bitstream framing.
dream_h263.c:         Attributed internal H.263/Sorenson conversion.
dream_overlay.c:      Timed application-surface overlay composition.

The decoder transport is the Dream 168-byte VIDEO_SET_FRAME ioctl, not a write
to a generic Amlogic device. SYSINFO is the 48-byte AArch64 structure. The build
rejects 32-bit userspace. Eight-piece frame ABI is retained in the backend;
normalized AUs currently use one contiguous piece. Unmodified elementary AUs
retain a GstBuffer reference without an additional payload copy.

Data is bounded by bytes and frames, including the in-flight frame. The byte
backpressure threshold may be exceeded by one AU of at most min(threshold,
32 MiB); VC-1 additionally retains at most one 32 MiB AU for lookahead. This
keeps codec state advancement and queue publication atomic across pause/flush.
EAGAIN and
EINTR keep the unaccepted suffix alive; pause keeps its offset. Flush/caps
changes invalidate a generation, so old compressed data is not replayed into
a newly configured decoder. Permanent decoder errors stop retrying and post
a bus error. A no-progress timeout excludes paused time. This cannot cancel an
ioctl blocked inside a broken kernel driver; O_NONBLOCK depends on the driver.

The selected audio sink and GStreamer clock retain their existing roles. This
sink does not introduce a second independent A/V-control loop. The current
OpenATV Dream Two test image uses ALSA (dreamhdmi), not dreamaudiosink; compare
both video implementations with the same available audio sink. Decoder PTS uses the 90 kHz
clock, while codec framerate configuration uses 96 kHz frame duration.

Public compatibility
--------------------
Factory: dreamvideosink, hardware sink rank PRIMARY.
get-decoder-time: signed 64-bit nanoseconds; -1 if unavailable.
get-overlay-compositor: borrowed pointer to GstDreamOverlayCompositor.
Bus messages: eventSizeAvail/Changed, eventFrameRateAvail/Changed,
              eventProgressiveAvail/Changed with existing E2 field names.
GstVideoOverlay interface and overlay-composition caps/allocation negotiation.
No E2 Python or public receiver setting is changed by this package.

Build
-----
Linux, 64-bit userspace, C11, autotools/libtool, GStreamer >= 1.14 (core/base/
video/codecparsers), GLib threads and libudev. Additional codec dependencies, if introduced,
are authoritative in configure.ac and the BitBake recipe.

  autoreconf -fi
  ./configure --prefix=/usr --enable-tests
  make -j4
  make check

Tests additionally require gstreamer-check-1.0. They inject a decoder backend;
they do not open receiver devices. They cover framing, ABI, errors, lifecycle,
flush/EOS, timestamp conversion and overlay surface/timing behavior.

The adjacent recipe builds for dreamone/dreamtwo and installs the normal
GStreamer plugin. It conflicts with gstreamer1.0-dreamsinks because two modules
exporting the same factory must not be installed together. The machine image
selection is intentionally not switched before the hardware/parity gates pass.
For comparisons use separate processes and registries, one implementation at a
time. Never run two video pipelines against the hardware decoder concurrently.

Provenance and distribution
--------------------------
New code is marked LGPL-2.1-or-later where stated in its file header; COPYING
contains that license. Any internally incorporated third-party converter keeps
its original notices and separate terms. A package containing such a converter
is NOT uniformly LGPL and must not be published as though it were. Its recipe
and provenance ledger describe the combined internal-only status.

Reference binary: libgstdreamsinks.so from gstreamer1.0-dreamsinks 1.17 aarch64.
SHA256: 994ea5cc997739fd81f936c75f5b4636ec67840b828e5a936f89fcd421c2ecc9.
REA evidence is kept separately in the workspace; source files are a fresh
implementation of the observed ABI and standard GStreamer contracts, except
explicitly attributed third-party source. No proprietary binary is packaged.
