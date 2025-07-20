#!/bin/bash

# =================================================================
# Multiboot Selector - BusyBox Compatible Version
# =================================================================
#
# This script allows you to select a different Multiboot image
# by updating the STARTUP file on OpenATV devices.
#
# It dynamically detects available STARTUP and STARTUP_* files
# and validates images by mounting their corresponding root device.
#
# You can pass a slot index via parameter to avoid user interaction
# or pass 'list' to get only the list of available slots.
# 
# =================================================================

#!/bin/sh
echo "Multiboot Selector - Starting..."

known_distros='
"beyonwiz": "Beyonwiz",
"blackhole": "Black Hole",
"egami": "EGAMI",
"openatv": "OpenATV",
"openbh": "OpenBH",
"opendroid": "OpenDroid",
"openeight": "OpenEight",
"openhdf": "OpenHDF",
"opennfr": "OpenNFR",
"openpli": "OpenPLi",
"openspa": "OpenSpa",
"openvision": "Open Vision",
"openvix": "OpenViX",
"sif": "Sif",
"teamblue": "teamBlue",
"vti": "VTi",
"newnigma2": "Newnigma2",
"Dreambox": "DreamOS",
"opendreambox": "OpenDreambox",
"unknown": "Unknown Image/Distro",
'
image_info() {
    idx=$1
    ROOT_PARTITION="${ROOT_PARTITIONS[$idx]}"
    ROOT_SUBDIR="${ROOT_SUBDIRS[$idx]}"
    ROOTFS_TYPE="${ROOTFS_TYPES[$idx]}"
    STARTUP_FILE="${STARTUP_FILES[$idx]}"

    tmpdir="$(mktemp -d)/"
    [ "$ROOTFS_TYPE" == "ubifs" -o "$ROOTFS_TYPE" == "ext4" ] && mount_option="-t $ROOTFS_TYPE"
    mount $mount_option "$ROOT_PARTITION" "$tmpdir" &>/dev/null

    if [[ "$STARTUP_FILE" == *FLASH* ]] && [ "$ROOTFS_TYPE" == "ubifs" ]; then
        type="UBI"
    elif [[ "$STARTUP_FILE" == *FLASH* ]]; then
        type="FLASH"
    elif [[ "$ROOT_PARTITION" == *mmcblk* ]]; then
        type="eMMC"
    elif [[ "$ROOT_PARTITION" == *mtd* ]]; then
        type="MTD"
    elif [[ "$ROOT_PARTITION" == *ubi* ]]; then
        type="UBI"
    else
        type="USB"
    fi

    enigma_file_binary="$tmpdir$ROOT_SUBDIR/usr/bin/enigma2"
    distro_file_enigma="$tmpdir$ROOT_SUBDIR/usr/lib/enigma.info"
    distro_file_image="$tmpdir$ROOT_SUBDIR/etc/image-version"
    distro_file_issue="$tmpdir$ROOT_SUBDIR/etc/issue"
    distro="unknown"

    if [ -f "$enigma_file_binary" ]; then
        e2date=$(stat -c %z "$enigma_file_binary" | cut -d ' ' -f 1 | xargs)

        if [ -f "$distro_file_enigma" ]; then
            distro=$(grep '^distro=' "$distro_file_enigma" | cut -d '=' -f 2 | xargs)
            version=$(grep '^imgversion=' "$distro_file_enigma" | cut -d '=' -f 2 | xargs)
            compiledate=$(grep '^compiledate=' "$distro_file_enigma" | cut -d '=' -f 2 | xargs)
            date="${compiledate:0:4}-${compiledate:4:2}-${compiledate:6:2}"
        elif [ -f "$distro_file_image" ]; then
            distro=$(grep '^distro=' "$distro_file_image" | cut -d '=' -f 2 | xargs)
            version=$(grep '^version=' "$distro_file_image" | cut -d '=' -f 2 | xargs)
        fi

        [ -z "$date" ] && date="$e2date"
        [ -z "$distro" ] && distro=$(cat "$distro_file_issue" | head -n 1 | cut -d ' ' -f 1 | xargs)
        dmatch=$(echo "$known_distros" | grep -oi "\"$distro\": \"[^\"]*\"")
        [ -n "$dmatch" ] && distro=$(echo "$dmatch" | sed -E 's/.*: "(.*)"/\1/')
        [ -z "$version" ] && version=$(cat "$distro_file_issue" | head -n 1 | cut -d ' ' -f 2 | xargs)

        cmp -s "/boot/STARTUP" "/boot/$STARTUP_FILE" &>/dev/null && current=' - Current'
        printf "Slot $type: $(echo $distro $version | xargs) ($date)$current"
    else
        printf "Slot $type: Empty"
    fi
    umount -f "$tmpdir" &>/dev/null && rm -rf "$tmpdir"
}

select_image() {
    idx=$1
    echo "Image ${choices[$idx]} selected"
    ROOT_PARTITION="${ROOT_PARTITIONS[$idx]}"
    KERNEL_PATH="${KERNEL_PATHS[$idx]}"
    ROOT_SUBDIR="${ROOT_SUBDIRS[$idx]}"
    ROOTFS_TYPE="${ROOTFS_TYPES[$idx]}"
    STARTUP_FILE="${STARTUP_FILES[$idx]}"
}

image_choice="$1"
images=()
choices=()
ROOT_PARTITIONS=()
KERNEL_PATHS=()
ROOT_SUBDIRS=()
STARTUP_FILES=()

if grep -q -E "dm820|dm7080|dm900|dm920" /proc/stb/info/model 2>/dev/null || grep -q -E "beyonwizu4|et11000|sf4008" /proc/stb/info/boxtype 2>/dev/null; then
    BOOT="/dev/mmcblk0boot1"
elif grep -q 'kexec=1' /proc/cmdline; then
    for dev in /dev/mmcblk0p{4,7,9}; do
        type=$(blkid -s TYPE -o value "$dev" 2>/dev/null)
        if [ -n "$type" ]; then
            BOOT="$dev"
            break
        fi
    done
else
    for i in /sys/block/mmcblk0/mmcblk0p*; do
        if [ -f "$i/uevent" ]; then
            partname=$(grep '^PARTNAME=' "$i/uevent" | cut -d '=' -f 2)
            devname=$(grep DEVNAME "$i/uevent" | cut -d '=' -f 2)
            case "$partname" in
                others|startup)
                    BOOT="/dev/$devname"
                    ;;
                other2)
                    BOOT="/dev/mmcblk0boot1"
                    ;;
            esac
        fi
    done
fi

if [ -z "$BOOT" ]; then
    for dev in /dev/sd[a-d]1; do
        label=$(blkid -s LABEL -o value "$dev" 2>/dev/null)
        if [ "$label" = "STARTUP" ]; then
            BOOT="$dev"
            break
        fi
    done
fi

echo "BOOT found: $BOOT"

(echo 0 > /sys/block/mmcblk0boot1/force_ro) 2>/dev/null
mkdir -p /boot 2>/dev/null
mount -t vfat "$BOOT" /boot 2>/dev/null

FILE="/boot/STARTUP_FLASH"
if [ -f "$FILE" ]; then
    ROOT=""
    ROOTSUBDIR=""
    KERNEL=""

    while IFS= read -r line || [ -n "$line" ]; do
        ROOT=$(echo "$line" | sed -n 's/.*root=\([^ ]*\).*/\1/p')
        ROOTSUBDIR=$(echo "$line" | sed -n 's/.*rootsubdir=\([^ ]*\).*/\1/p')
        ROOTFSTYPE=$(echo "$line" | sed -n 's/.*rootfstype=\([^ ]*\).*/\1/p')
        KERNEL=$(echo "$line" | sed -n 's/.*kernel=\([^ ]*\).*/\1/p')
    done < "$FILE"

    if [ -n "$ROOT" ] && [ -n "$KERNEL" ]; then
        choices+=("F")
        ROOT_PARTITIONS+=("$ROOT")
        KERNEL_PATHS+=("$KERNEL")
        ROOT_SUBDIRS+=("$ROOTSUBDIR")
        ROOTFS_TYPES+=("$ROOTFSTYPE")
        STARTUP_FILES+=("STARTUP_FLASH")
        images+=("$(image_info "0")")
    fi
fi

FILE="/boot/STARTUP_RECOVERY"
if [ -f "$FILE" ]; then
    ROOT=""
    ROOTSUBDIR=""
    KERNEL=""

    while IFS= read -r line || [ -n "$line" ]; do
        ROOT=$(echo "$line" | sed -n 's/.*root=\([^ ]*\).*/\1/p')
        ROOTSUBDIR=$(echo "$line" | sed -n 's/.*rootsubdir=\([^ ]*\).*/\1/p')
        ROOTFSTYPE=$(echo "$line" | sed -n 's/.*rootfstype=\([^ ]*\).*/\1/p')
        KERNEL=$(echo "$line" | sed -n 's/.*kernel=\([^ ]*\).*/\1/p')
    done < "$FILE"

    if [ -n "$ROOT" ] && [ -n "$KERNEL" ]; then
        choices+=("R")
        ROOT_PARTITIONS+=("$ROOT")
        KERNEL_PATHS+=("$KERNEL")
        grep -q 'kexec=1' /proc/cmdline && ROOT_SUBDIRS+=("") || ROOT_SUBDIRS+=("$ROOTSUBDIR")
        ROOTFS_TYPES+=("$ROOTFSTYPE")
        STARTUP_FILES+=("STARTUP_RECOVERY")
        images+=("$(image_info "0")")
    fi
fi

for i in $(seq 1 15); do
    FILE="/boot/STARTUP_$i"
    if [ -f "$FILE" ]; then
        ROOT=""
        ROOTSUBDIR=""
        KERNEL=""

        while IFS= read -r line || [ -n "$line" ]; do
            ROOT=$(echo "$line" | sed -n 's/.*root=\([^ ]*\).*/\1/p')
            ROOTSUBDIR=$(echo "$line" | sed -n 's/.*rootsubdir=\([^ ]*\).*/\1/p')
            ROOTFSTYPE=$(echo "$line" | sed -n 's/.*rootfstype=\([^ ]*\).*/\1/p')
            KERNEL=$(echo "$line" | sed -n 's/.*kernel=\([^ ]*\).*/\1/p')
        done < "$FILE"

        if [ -n "$ROOT" ] && [ -n "$KERNEL" ]; then
            choices+=("$i")
            ROOT_PARTITIONS+=("$ROOT")
            KERNEL_PATHS+=("$KERNEL")
            ROOT_SUBDIRS+=("$ROOTSUBDIR")
            ROOTFS_TYPES+=("$ROOTFSTYPE")
            STARTUP_FILES+=("STARTUP_$i")
            images+=("$(image_info "$((${#choices[@]} - 1))")")
        fi
    fi
done

if [ ${#images[@]} -eq 0 ]; then
    echo "No available images to select from."
    umount /boot 2>/dev/null
    exit 1
fi

if [ -z "$image_choice" -o "$image_choice" == "list" ]; then
    echo "Please select an image:"
    for i in "${!images[@]}"; do
        echo "${choices[$i]}) ${images[$i]}"
    done
    [ -z "$image_choice" ] && read -p "Select an image (number): " image_choice
    [ "$image_choice" == "list" ] && exit 0
fi

valid_choice=false
for i in "${!choices[@]}"; do
    if [ "${choices[$i]}" = "$image_choice" ]; then
        choice_index="$i"
        valid_choice=true
        break
    fi
done

if ! $valid_choice; then
    echo "Invalid selection: $image_choice"
    umount /boot 2>/dev/null
    exit 1
fi

select_image "$choice_index"

if mountpoint -q "/tmp/root"; then
    echo "Unmounting /tmp/root..."
    umount /tmp/root
fi
for i in $(seq 1 15); do
    if mountpoint -q "/var/volatile/tmp/root$i"; then
        echo "Unmounting /var/volatile/tmp/root$i..."
        umount "/var/volatile/tmp/root$i"
    fi
done

echo "Copying /boot/$STARTUP_FILE to /boot/STARTUP..."
cp "/boot/$STARTUP_FILE" "/boot/STARTUP"

echo "Selected ROOT partition: $ROOT_PARTITION"
echo "Selected ROOTSUBDIR: $ROOT_SUBDIR"
sync
echo "Script finished."

umount /boot 2>/dev/null
