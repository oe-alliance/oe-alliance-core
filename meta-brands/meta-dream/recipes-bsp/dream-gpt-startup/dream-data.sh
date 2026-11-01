#!/bin/sh
# startup.sh mounts /data first; mount lists it by its mmcblk name, not by /dev/dreambox-data
grep -qs " /data " /proc/mounts || exit 0

# A card without the DREAMCARD marker (e.g. dumbo) boots without bootconfig.txt;
# /data belongs to the images in the eMMC then.
case " $(cat /proc/cmdline) " in
  *" root=/dev/mmcblk1p"*)
    [ "$(blkid -s LABEL -o value /dev/mmcblk1p1 2>/dev/null)" = "DREAMCARD" ] || exit 0 ;;
esac

# Slot 1's GPT label is 'dreambox-rootfs' (no suffix); slots 2+ have a numeric suffix.
# The SD card slots are labelled 'dreambox-rootfs' too, so /dev/disk/by-partlabel
# may point to one of them; ask the eMMC partitions directly.
emmc_parts=""
for dev in /dev/mmcblk0p*; do
  case "$(blkid -s PARTLABEL -o value "$dev")" in
    dreambox-rootfs|dreambox-rootfs[0-9]*) emmc_parts="$emmc_parts ${dev##/dev/mmcblk0p}" ;;
  esac
done
emmc_parts=$(echo "$emmc_parts" | tr ' ' '\n' | sort -nu | tr '\n' ' ')
emmc_count=$(echo "$emmc_parts" | wc -w)

# SD-card boot slots only if p1 is the GPTSlotManager 'DREAMCARD' FAT16 marker.
sd_parts=""
if [ -b /dev/mmcblk1p1 ] && \
   [ "$(blkid -s TYPE -o value /dev/mmcblk1p1 2>/dev/null)" = "vfat" ] && \
   [ "$(blkid -s LABEL -o value /dev/mmcblk1p1 2>/dev/null)" = "DREAMCARD" ]; then
  for partno in $(seq 2 20); do
    dev=/dev/mmcblk1p${partno}
    [ -b "$dev" ] || break
    [ "$(blkid -s TYPE -o value "$dev" 2>/dev/null)" = "ext4" ] || break
    sd_parts="$sd_parts $partno"
  done
fi

# STARTUP_<n> is rewritten only when its root= names another partition, e.g.
# after an older version of this script missed a slot; other changes stay.
write_startup() {
  [ "$(grep -o 'root=[^ ]*' "$1" 2>/dev/null | head -n 1)" = "${2%% *}" ] || echo "$2" > "$1"
}

# /data/STARTUP holds the active slot's cmdline; e2 rewrites it on slot switch.
n=0
for partno in $emmc_parts; do
  n=$((n + 1))
  cmdline="root=/dev/mmcblk0p${partno} rootfstype=ext4 kernel=/boot/kernel.img"
  write_startup "/data/STARTUP_${n}" "$cmdline"
  [ "$n" = "1" ] && [ ! -f /data/STARTUP ] && echo "$cmdline" > /data/STARTUP
done
for partno in $sd_parts; do
  n=$((n + 1))
  cmdline="root=/dev/mmcblk1p${partno} rootfstype=ext4 kernel=/kernel${partno}.img"
  write_startup "/data/STARTUP_${n}" "$cmdline"
done
total_slots=$n

[ -f /data/STARTUP_RECOVERY ] || echo "bootcmd= recovery" > /data/STARTUP_RECOVERY

# The boot menu forgets the entry started last whenever bootconfig.txt is written
# (new inode or ctime), so all changes go to a copy and the file is replaced only
# when the copy differs.
bootconfig="/data/bootconfig.txt"
input_file="/tmp/bootconfig.txt"
temp_file="/tmp/temp_file.txt"
rm -f "$input_file"
[ -f "$bootconfig" ] && cp "$bootconfig" "$input_file"

is_unlocked=1
if [ -f "$input_file" ] && grep -q -i '^lock=' "$input_file" && ! grep -q -i '^lock=0' "$input_file"; then
  is_unlocked=0
fi

write_emmc_section() {
  idx="$1"; partno="$2"
  echo "[BuildIn Slot $idx]"
  echo "cmd=ext4load mmc 1:$partno 1080000 /boot/kernel.img;bootm;"
  echo "arg=\${bootargs} logo=osd0,loaded,0x7f800000 vout=1080p50hz,enable hdmimode=1080p50hz fb_width=1280 fb_height=720 panel_type=lcd_4"
}

write_sd_section() {
  idx="$1"; partno="$2"
  echo "[SDcard Slot $idx]"
  echo "cmd=fatload mmc 0:1 1080000 /kernel${partno}.img;bootm;"
  echo "arg=\${bootargs} logo=osd0,loaded,0x7f800000 vout=1080p50hz,enable hdmimode=1080p50hz fb_width=1280 fb_height=720 panel_type=lcd_4"
}

write_recovery_section() {
  echo "[   Recovery   ]"
  echo 'cmd=imgread kernel recovery ${loadaddr} 0; bootm ${loadaddr};'
  echo 'arg=${bootargs} logo=osd0,loaded,0x7f800000 vout=1080p50hz,enable hdmimode=1080p50hz fb_width=1280 fb_height=720 panel_type=lcd_4'
}

if [ "$is_unlocked" = "1" ]; then
  count_cmd=$(grep -c '^cmd=' "$input_file" 2>/dev/null || echo 0)
  if [ "$count_cmd" -le 2 ]; then
    {
      echo "default=0"
      echo "details=0"
      echo "timeout=3"
      echo "lock=0"
      if [ "$total_slots" -gt 4 ]; then
        echo "fb_pos=100,450"
        echo "fb_size=1080,300"
        echo "font_size=2"
      else
        echo "fb_pos=100,400"
        echo "fb_size=1080,300"
      fi
      n=0
      for partno in $emmc_parts; do
        n=$((n + 1))
        write_emmc_section "$n" "$partno"
      done
      for partno in $sd_parts; do
        n=$((n + 1))
        write_sd_section "$n" "$partno"
      done
      write_recovery_section
    } > "$temp_file"
    mv "$temp_file" "$input_file"
  else
    # Recovery is matched by its cmd= line so a renamed header (e.g. [Dreambox Recovery]) still works.
    recovery_section=""
    if grep -q '^cmd=imgread kernel recovery' "$input_file"; then
      cmd_line=$(grep -n '^cmd=imgread kernel recovery' "$input_file" | head -1 | cut -d: -f1)
      header_line=$((cmd_line - 1))
      arg_line=$((cmd_line + 1))
      prev=$(sed -n "${header_line}p" "$input_file")
      case "$prev" in
        \[*\])
          recovery_section=$(sed -n "${header_line},${arg_line}p" "$input_file")
          sed -i "${header_line},${arg_line}d" "$input_file"
          ;;
      esac
    fi
    sed -i -e :a -e '/^$/{$d;N;ba' -e '}' "$input_file" 2>/dev/null || true

    n=0
    for partno in $emmc_parts; do
      n=$((n + 1))
      if ! grep -q "^cmd=ext4load mmc 1:${partno} " "$input_file"; then
        {
          cat "$input_file"
          write_emmc_section "$n" "$partno"
        } > "$temp_file"
        mv "$temp_file" "$input_file"
      fi
    done
    for partno in $sd_parts; do
      n=$((n + 1))
      if ! grep -q "^cmd=fatload mmc 0:1 1080000 /kernel${partno}\.img" "$input_file"; then
        {
          cat "$input_file"
          write_sd_section "$n" "$partno"
        } > "$temp_file"
        mv "$temp_file" "$input_file"
      fi
    done

    if [ -n "$recovery_section" ]; then
      {
        cat "$input_file"
        echo "$recovery_section"
      } > "$temp_file"
      mv "$temp_file" "$input_file"
    else
      {
        cat "$input_file"
        write_recovery_section
      } > "$temp_file"
      mv "$temp_file" "$input_file"
    fi
  fi
fi

desired_arg="arg=\${bootargs} logo=osd0,loaded,0x7f800000 vout=1080p50hz,enable hdmimode=1080p50hz fb_width=1280 fb_height=720 panel_type=lcd_4"
awk -v desired_arg="$desired_arg" '
{
    if ($0 == "[   Recovery]") {
        in_recovery_section = !in_recovery_section;
    }

    if (in_recovery_section && $0 == "arg=\${bootargs}") {
        print $0;
    } else if (in_recovery_section) {
        print $0;
    } else {
        if ($0 == "arg=\${bootargs}") {
            print desired_arg;
        } else {
            print $0;
        }
    }
}
' "$input_file" > "$temp_file"
mv "$temp_file" "$input_file"

if ! cmp -s "$input_file" "$bootconfig"; then
  cp "$input_file" /data/bootconfig_temp.txt && mv /data/bootconfig_temp.txt "$bootconfig"
fi
rm -f "$input_file"
[ "$(stat -c %a:%U "$bootconfig")" = "755:root" ] || { chmod 755 "$bootconfig"; chown root:root "$bootconfig"; }

: exit 0
