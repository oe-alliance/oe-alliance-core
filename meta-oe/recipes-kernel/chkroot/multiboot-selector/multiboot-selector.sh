#!/bin/bash

# ===============================================================
# Multiboot Selector - BusyBox Compatible Version
# ===============================================================
#
# This script allows you to select a different Multiboot image
# by updating the STARTUP file on OpenATV devices.
#
# It dynamically detects available STARTUP and STARTUP_* files
# and validates images by mounting their corresponding root device.
#
# You can pass a image/slot number via parameter to avoid user
# interaction.
# 
# ===============================================================

#!/bin/sh
echo "Multiboot Selector - Starting..."

select_image() {
    idx=$1
    echo "Image ${choices[$idx]} selected"
    ROOT_PARTITION="${ROOT_PARTITIONS[$idx]}"
    KERNEL_PATH="${KERNEL_PATHS[$idx]}"
    ROOT_SUBDIR="${ROOT_SUBDIRS[$idx]}"
    STARTUP_FILE="${STARTUP_FILES[$idx]}"
}

images=()
choices=()
ROOT_PARTITIONS=()
KERNEL_PATHS=()
ROOT_SUBDIRS=()
STARTUP_FILES=()

if grep -q -E "dm820|dm7080|dm900|dm920" /proc/stb/info/model || grep -q -E "beyonwizu4|et11000|sf4008" /proc/stb/info/boxtype; then
	BOOT="/dev/mmcblk0boot1"
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

echo 0 > /sys/block/mmcblk0boot1/force_ro
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
        KERNEL=$(echo "$line" | sed -n 's/.*kernel=\([^ ]*\).*/\1/p')
    done < "$FILE"

    if [ -n "$ROOT" ] && [ -n "$KERNEL" ]; then
        images+=("Image 0 (Flash)")
        choices+=("0")
        ROOT_PARTITIONS+=("$ROOT")
        KERNEL_PATHS+=("$KERNEL")
        ROOT_SUBDIRS+=("$ROOTSUBDIR")
        STARTUP_FILES+=("STARTUP_FLASH")
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
            KERNEL=$(echo "$line" | sed -n 's/.*kernel=\([^ ]*\).*/\1/p')
        done < "$FILE"

        if [ -n "$ROOT" ] && [ -n "$KERNEL" ]; then
            images+=("Image $i")
            choices+=("$i")
            ROOT_PARTITIONS+=("$ROOT")
            KERNEL_PATHS+=("$KERNEL")
            ROOT_SUBDIRS+=("$ROOTSUBDIR")
            STARTUP_FILES+=("STARTUP_$i")
        fi
    fi
done

if [ ${#images[@]} -eq 0 ]; then
    echo "No available images to select from."
    umount /boot 2>/dev/null
    exit 1
fi

image_choice="$1"
if [ -z "$image_choice" ]; then
    echo "Please select an image:"
    for i in "${!images[@]}"; do
        echo "${choices[$i]}) ${images[$i]}"
    done
    read -p "Select an image (number): " image_choice
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
