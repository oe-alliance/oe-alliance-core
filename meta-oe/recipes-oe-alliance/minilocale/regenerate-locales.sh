#!/usr/bin/env bash
# Regenerate minilocale's locales.tar.gz + c-utf-8.tar.gz from OE's
# deployed glibc-binary-localedata ipk packages — the exact output of
# the target glibc's localedef.
#
# When to run: whenever the target glibc is bumped and the locale data
# should follow. Rare, maybe once every couple of years.
#
# How to run:
#   1. Temporarily move the glibc-locale bbappend aside so the ipks get
#      generated with binary locale content:
#        mv meta-oe/recipes-core/glibc/glibc-locale_2.%.bbappend{,.OFF}
#
#   2. Rebuild glibc-locale so the ipks land in tmp/deploy/ipk:
#        bitbake -c cleansstate glibc-locale && bitbake glibc-locale
#
#   3. Run this script from any working directory inside the build tree
#      (or set BUILDDIR / IPK_DIR):
#        CURRENT_DIR=<layer>/meta-oe/recipes-oe-alliance/minilocale/minilocale \
#          <layer>/meta-oe/recipes-oe-alliance/minilocale/regenerate-locales.sh
#
#   4. Copy the generated tarballs from ./minilocale-out/ into the recipe
#      files dir, bump PKGV in minilocale.bb, restore the bbappend.
#
# Preconditions: bash 4+, ar, tar.
#
# The script does three non-obvious things worth knowing:
#   * C.UTF-8 in modern glibc (>= 2.35) ships stub LC_COLLATE/LC_CTYPE
#     because runtime handles them via byte-order. minilocale.bb symlinks
#     other locales onto C.UTF-8 for small-flash boxes though, so we copy
#     the full en_US versions on top of the stubs.
#   * LC_CTYPE and LC_COLLATE are identical across many locales that
#     share the same script/language family. We deduplicate them to
#     relative symlinks so the tarball stays small.
#   * The dedup uses LC_ALL=C sort so C.UTF-8 stays as the canonical
#     source and never gets converted into a symlink itself.

set -euo pipefail

LANGUAGES="
    ar_AE bg_BG ca_AD cs_CZ da_DK de_DE el_GR en_AU en_GB en_US es_ES et_EE
    fa_IR fi_FI fr_FR fy_NL he_IL hr_HR hu_HU id_ID is_IS it_IT lt_LT lv_LV
    nb_NO nn_NO nl_NL pl_PL pt_BR pt_PT ro_RO ru_RU sk_SK sl_SI sr_RS sv_SE
    th_TH tr_TR uk_UA zh_CN zh_HK
"

IPK_DIR="${IPK_DIR:-}"
if [ -z "$IPK_DIR" ]; then
    IPK_DIR=$(find "${BUILDDIR:-$PWD}/tmp/deploy/ipk" -maxdepth 1 -type d 2>/dev/null | while read d; do
        [ -n "$(ls "$d"/glibc-binary-localedata-* 2>/dev/null | head -1)" ] && echo "$d" && break
    done | head -1)
    if [ -z "$IPK_DIR" ]; then
        echo "ERROR: no ipk dir with glibc-binary-localedata found." >&2
        echo "  set IPK_DIR=/path/to/tmp/deploy/ipk/<arch>" >&2
        exit 1
    fi
fi

echo "== IPK source: $IPK_DIR"

OUT="${OUT:-$PWD/minilocale-out}"
STAGE="$OUT/stage"
LOCDIR="$STAGE/usr/lib/locale"
TMPEX="$OUT/tmp-extract"
rm -rf "$OUT"
mkdir -p "$LOCDIR" "$TMPEX"

extract_ipk() {
    local ipk="$1" dest="$2"
    mkdir -p "$dest"
    ( cd "$dest" && ar x "$ipk" && tar xf data.tar.* && rm -f data.tar.* control.tar.* debian-binary )
}

FOUND=()
MISSING=()

for LOC in $LANGUAGES; do
    printf "  %-8s  " "$LOC"
    lc=$(echo "$LOC" | tr '[:upper:]_' '[:lower:]-')
    IPK=$(ls "$IPK_DIR"/glibc-binary-localedata-${lc}_*.ipk 2>/dev/null | head -1)
    if [ -z "$IPK" ]; then
        echo "MISSING (no ipk)"
        MISSING+=("$LOC")
        continue
    fi
    rm -rf "$TMPEX"/*
    extract_ipk "$IPK" "$TMPEX"
    LOCSRC=$(find "$TMPEX/usr/lib/locale" -mindepth 1 -maxdepth 1 -type d 2>/dev/null | head -1)
    if [ -z "$LOCSRC" ]; then
        echo "MISSING (no locale dir in ipk)"
        MISSING+=("$LOC")
        continue
    fi
    cp -r "$LOCSRC" "$LOCDIR/$LOC"
    echo "OK ($(du -sh "$LOCDIR/$LOC" | cut -f1)) from $(basename "$IPK")"
    FOUND+=("$LOC")
done

echo
echo "== Grabbing C.UTF-8 =="
CIPK=$(ls "$IPK_DIR"/glibc-binary-localedata-c_*.ipk 2>/dev/null | head -1)
if [ -n "$CIPK" ]; then
    rm -rf "$TMPEX"/*
    extract_ipk "$CIPK" "$TMPEX"
    CSRC=$(find "$TMPEX/usr/lib/locale" -mindepth 1 -maxdepth 1 -type d 2>/dev/null | head -1)
    [ -n "$CSRC" ] && cp -r "$CSRC" "$LOCDIR/C.UTF-8" && echo "  OK from $(basename "$CIPK")"
else
    echo "  WARN: no glibc-binary-localedata-c_*.ipk found."
    echo "        Falling back to host localedef for C.UTF-8 only."
    localedef --prefix="$STAGE" --no-archive -i C -c -f UTF-8 C.UTF-8 2>/dev/null || true
    [ -d "$LOCDIR/C.utf8" ] && mv "$LOCDIR/C.utf8" "$LOCDIR/C.UTF-8"
fi

echo
echo "== Ensuring C.UTF-8 has full LC_CTYPE and LC_COLLATE =="
for name in LC_CTYPE LC_COLLATE; do
    src="$LOCDIR/en_US/$name"
    dst="$LOCDIR/C.UTF-8/$name"
    if [ -f "$src" ] && [ ! -L "$src" ]; then
        srcsize=$(stat -c%s "$src")
        dstsize=$(stat -c%s "$dst" 2>/dev/null || echo 0)
        if [ "$dstsize" -lt "$((srcsize / 4))" ]; then
            printf "  %-12s  C.UTF-8 stub (%d B) → copy from en_US (%d B)\n" \
                "$name" "$dstsize" "$srcsize"
            cp "$src" "$dst"
        fi
    fi
done

echo
echo "== Deduplicating identical LC_CTYPE / LC_COLLATE across locales =="
dedup_file() {
    local name="$1"
    declare -A seen
    local total=0 deduped=0
    local ordered
    ordered=$( { [ -d "$LOCDIR/C.UTF-8" ] && echo "C.UTF-8"; \
                 LC_ALL=C ls -1 "$LOCDIR" | LC_ALL=C sort | grep -v '^C\.UTF-8$'; } )
    for locdir in $ordered; do
        local f="$LOCDIR/$locdir/$name"
        [ -f "$f" ] || continue
        [ -L "$f" ] && continue
        total=$((total + 1))
        local hash
        hash=$(md5sum "$f" | awk '{print $1}')
        if [ -n "${seen[$hash]:-}" ]; then
            [ "$locdir" = "C.UTF-8" ] && continue
            local canon="${seen[$hash]}"
            rm "$f"
            ln -s "../$canon/$name" "$f"
            deduped=$((deduped + 1))
        else
            seen[$hash]="$locdir"
        fi
    done
    printf "  %-12s  %d/%d files → symlinks (%d unique retained)\n" \
        "$name" "$deduped" "$total" "$((total - deduped))"
}
dedup_file LC_CTYPE
dedup_file LC_COLLATE

echo
echo "== Packing locales.tar.gz (excludes C.UTF-8) =="
tar czf "$OUT/locales.tar.gz" -C "$STAGE" \
    --exclude='usr/lib/locale/C.UTF-8' \
    usr/lib/locale
ls -la "$OUT/locales.tar.gz"

echo
echo "== Packing c-utf-8.tar.gz =="
if [ -d "$LOCDIR/C.UTF-8" ]; then
    tar czf "$OUT/c-utf-8.tar.gz" -C "$STAGE" usr/lib/locale/C.UTF-8
    ls -la "$OUT/c-utf-8.tar.gz"
fi

rm -rf "$TMPEX"

echo
echo "== Summary =="
echo "  Found:   ${#FOUND[@]} / $(echo $LANGUAGES | wc -w)"
if [ ${#MISSING[@]} -gt 0 ]; then
    echo "  Missing: ${MISSING[*]}"
fi

echo
echo "== Size comparison =="
CURRENT_DIR="${CURRENT_DIR:-}"
if [ -n "$CURRENT_DIR" ] && [ -d "$CURRENT_DIR" ]; then
    for f in locales.tar.gz c-utf-8.tar.gz; do
        old=$(stat -c%s "$CURRENT_DIR/$f" 2>/dev/null || echo 0)
        new=$(stat -c%s "$OUT/$f" 2>/dev/null || echo 0)
        printf "  %-20s  old=%9d  new=%9d  delta=%+d\n" "$f" "$old" "$new" "$((new - old))"
    done
fi

echo
echo "Done. Files ready at $OUT/"
