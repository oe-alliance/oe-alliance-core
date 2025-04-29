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
# ===============================================================

# Multiboot Selector
echo "Multiboot Selector - Starting..."

# Function to select the correct ROOT, KERNEL, and Subdirectory
select_image() {
    case $image_choice in
        1)
            echo "Image 1 selected"
            ROOT_PARTITION="${ROOT_PARTITIONS[0]}"
            KERNEL_PATH="${KERNEL_PATHS[0]}"
            ROOT_SUBDIR="${ROOT_SUBDIRS[0]}"
            STARTUP_FILE="STARTUP_1"
            ;;
        2)
            echo "Image 2 selected"
            ROOT_PARTITION="${ROOT_PARTITIONS[1]}"
            KERNEL_PATH="${KERNEL_PATHS[1]}"
            ROOT_SUBDIR="${ROOT_SUBDIRS[1]}"
            STARTUP_FILE="STARTUP_2"
            ;;
        3)
            echo "Image 3 selected"
            ROOT_PARTITION="${ROOT_PARTITIONS[2]}"
            KERNEL_PATH="${KERNEL_PATHS[2]}"
            ROOT_SUBDIR="${ROOT_SUBDIRS[2]}"
            STARTUP_FILE="STARTUP_3"
            ;;
        4)
            echo "Image 4 selected"
            ROOT_PARTITION="${ROOT_PARTITIONS[3]}"
            KERNEL_PATH="${KERNEL_PATHS[3]}"
            ROOT_SUBDIR="${ROOT_SUBDIRS[3]}"
            STARTUP_FILE="STARTUP_4"
            ;;
        5)
            echo "Image 5 selected"
            ROOT_PARTITION="${ROOT_PARTITIONS[4]}"
            KERNEL_PATH="${KERNEL_PATHS[4]}"
            ROOT_SUBDIR="${ROOT_SUBDIRS[4]}"
            STARTUP_FILE="STARTUP_5"
            ;;
        6)
            echo "Image 6 selected"
            ROOT_PARTITION="${ROOT_PARTITIONS[5]}"
            KERNEL_PATH="${KERNEL_PATHS[5]}"
            ROOT_SUBDIR="${ROOT_SUBDIRS[5]}"
            STARTUP_FILE="STARTUP_6"
            ;;
        7)
            echo "Image 7 selected"
            ROOT_PARTITION="${ROOT_PARTITIONS[6]}"
            KERNEL_PATH="${KERNEL_PATHS[6]}"
            ROOT_SUBDIR="${ROOT_SUBDIRS[6]}"
            STARTUP_FILE="STARTUP_7"
            ;;
        8)
            echo "Image 8 selected"
            ROOT_PARTITION="${ROOT_PARTITIONS[7]}"
            KERNEL_PATH="${KERNEL_PATHS[7]}"
            ROOT_SUBDIR="${ROOT_SUBDIRS[7]}"
            STARTUP_FILE="STARTUP_8"
            ;;
        *)
            echo "Unknown selection."
            exit 1
            ;;
    esac
}

# List of available options
images=()
choices=()
ROOT_PARTITIONS=()
KERNEL_PATHS=()
ROOT_SUBDIRS=()

if grep -q -E "dm820|dm7080|dm900|dm920" /proc/stb/info/model || grep -q -E "beyonwizu4|et11000|sf4008" /proc/stb/info/boxtype; then
	BOOT="/dev/mmcblk0boot1"
else
	for i in /sys/block/mmcblk0/mmcblk0p*; do
		if [ -f "$i/uevent" ]; then
			partname=$(grep '^PARTNAME=' "$i/uevent" | cut -d '=' -f 2)
			devname=`cat /$i/uevent | grep DEVNAME | cut -d '=' -f 2`
			case "$partname" in
			  others)
				BOOT="/dev/$devname"
				;;
			  other2)
				BOOT="/dev/mmcblk0boot1"
				;;
			esac
		fi
	done
fi
echo "BOOT found:$BOOT"

echo 0 > /sys/block/mmcblk0boot1/force_ro
mount -t vfat "$BOOT" /boot 2>/dev/null

# Check all possible STARTUP files and partitions
for i in {1..8}; do
    STARTUP_FILE="/boot/STARTUP_$i"
    if [ -f "$STARTUP_FILE" ]; then
        # Read and parse the STARTUP_X file
        ROOT=""
        ROOTSUBDIR=""
        KERNEL=""
        
        while IFS= read -r line || [ -n "$line" ]; do
            # Extract root, rootsubdir and kernel
            ROOT=$(echo "$line" | sed -n 's/.*root=\([^ ]*\).*/\1/p')
            ROOTSUBDIR=$(echo "$line" | sed -n 's/.*rootsubdir=\([^ ]*\).*/\1/p')
            KERNEL=$(echo "$line" | sed -n 's/.*kernel=\([^ ]*\).*/\1/p')
        done < "$STARTUP_FILE"
        
        if [ -n "$ROOT" ] && [ -n "$ROOTSUBDIR" ] && [ -n "$KERNEL" ]; then
            # Add to available images if valid
            images+=("Image $i")
            choices+=("$i")
            ROOT_PARTITIONS+=("$ROOT")
            KERNEL_PATHS+=("$KERNEL")
            ROOT_SUBDIRS+=("$ROOTSUBDIR")
        fi
    fi
done

# No available images
if [ ${#images[@]} -eq 0 ]; then
    echo "No available images to select from."
    exit 1
fi

# Show available options
echo "Please select an image:"
for i in "${!images[@]}"; do
    echo "${choices[$i]}) ${images[$i]}"
done

# User selection
read -p "Select an image (1-8): " image_choice

# Check if the selection is valid
valid_choice=false
for i in "${!choices[@]}"; do
    if [ "${choices[$i]}" == "$image_choice" ]; then
        valid_choice=true
        break
    fi
done

if ! $valid_choice; then
    echo "Invalid selection. Please try again."
    exit 1
fi

# Select the image and set the correct partition, kernel, subfolder, and startup file
select_image

# Unmount existing mounts (if any)
if mountpoint -q "/tmp/root"; then
    echo "Unmounting existing mount from /tmp/root..."
    umount /tmp/root
fi

# Unmount the temporary root partitions if still mounted
for i in {1..8}; do
    if mountpoint -q "/var/volatile/tmp/root$i"; then
        echo "Unmounting existing mount from /var/volatile/tmp/root$i..."
        umount "/var/volatile/tmp/root$i"
    fi
done

# Copy the selected STARTUP file from /boot
echo "Copying /boot/$STARTUP_FILE to /boot/STARTUP..."
cp "/boot/$STARTUP_FILE" "/boot/STARTUP"

# Display selected options for debugging or logging
echo "Selected ROOT partition: $ROOT_PARTITION"
echo "Selected ROOTSUBDIR: $ROOT_SUBDIR"
sync
echo "Script finished."







