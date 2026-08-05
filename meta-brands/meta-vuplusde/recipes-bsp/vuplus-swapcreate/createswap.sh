#!/bin/sh

# Find a partition with PARTLABEL="swap"
swap_partition=$(blkid -t PARTLABEL="swap" -o device | head -n1)

if [ -z "$swap_partition" ]; then
    echo "Sorry, no swap partition found"
    exit 1
fi

# Check if the swap partition is already in /etc/fstab
if ! grep -q "$swap_partition" /etc/fstab; then
    mkswap "$swap_partition"
    swapon "$swap_partition"
    echo "$swap_partition none swap defaults 0 0" >> /etc/fstab
# Check if the swap partition is active
elif ! grep -q "$swap_partition" /proc/swaps; then
    mkswap "$swap_partition"
    swapon "$swap_partition"
fi


