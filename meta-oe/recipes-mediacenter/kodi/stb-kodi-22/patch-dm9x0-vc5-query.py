#!/usr/bin/env python3
"""Create a private DM9x0 libvc5dream with the verified query fix."""

from __future__ import annotations

import argparse
import hashlib
from pathlib import Path


EXPECTED_SHA256 = "df70548f41d84f475bb3709fe53cdd58f08d6d72407e4a97a5ee1e30b7c68ad2"
PATCHED_SHA256 = "911b87d589e3b8c700cdb0704f14d381478b8d4fa464800201d2ee9fcc61db88"
PATCH_OFFSET = 0x2C7C
ORIGINAL = bytes.fromhex(
    "48 20 a0 e3"  # mov   r2, #72
    "05 10 a0 e1"  # mov   r1, r5
    "02 00 8d e0"  # add   r0, sp, r2
    "22 f7 ff eb"  # bl    memcpy@plt
)
PATCHED = bytes.fromhex(
    "05 10 b0 e1"  # movs  r1, r5
    "48 20 a0 e3"  # mov   r2, #72
    "02 00 8d 10"  # addne r0, sp, r2
    "22 f7 ff 1b"  # blne  memcpy@plt
)


def digest(data: bytes) -> str:
    return hashlib.sha256(data).hexdigest()


def main() -> None:
    parser = argparse.ArgumentParser()
    parser.add_argument("source", type=Path)
    parser.add_argument("destination", type=Path)
    args = parser.parse_args()

    data = bytearray(args.source.read_bytes())
    source_digest = digest(data)
    if source_digest != EXPECTED_SHA256:
        raise SystemExit(
            f"refusing unknown libvc5dream binary: {source_digest} "
            f"(expected {EXPECTED_SHA256})"
        )

    found = bytes(data[PATCH_OFFSET : PATCH_OFFSET + len(ORIGINAL)])
    if found != ORIGINAL:
        raise SystemExit(
            f"unexpected instructions at 0x{PATCH_OFFSET:x}: {found.hex()}"
        )

    data[PATCH_OFFSET : PATCH_OFFSET + len(PATCHED)] = PATCHED
    patched_digest = digest(data)
    if patched_digest != PATCHED_SHA256:
        raise SystemExit(
            f"patched output hash mismatch: {patched_digest} "
            f"(expected {PATCHED_SHA256})"
        )

    args.destination.write_bytes(data)
    print(f"source sha256:  {source_digest}")
    print(f"patched sha256: {patched_digest}")


if __name__ == "__main__":
    main()
