#!/bin/sh
is_mounted() {
  mount | grep "$1"
}

while ! is_mounted "/dev/dreambox-data"; do
  sleep 1
done

if [ ! -f "/data/STARTUP_4" ]; then
  cp -f /usr/share/startup/* /data
fi

input_file="/data/bootconfig.txt"
temp_file="/tmp/temp_file.txt"
chmod 755 /data/bootconfig.txt
chgrp root /data/bootconfig.txt
chown root /data/bootconfig.txt
if [ -e "$input_file" ] && ! grep -q -i 'lock=' "$input_file" || grep -q -i 'lock=0' "$input_file"; then
  count_cmd=$(grep -o 'cmd=' "$input_file" | wc -l)
  if [ "$count_cmd" -le 2 ]; then
  new_content=$(cat <<EOF
default=0
details=0
timeout=3
lock=0
fb_pos=100,400
fb_size=1080,300
[BuildIn Slot 1]
cmd=ext4load mmc 1:5 1080000 /boot/kernel.img;bootm;
arg=\${bootargs} logo=osd0,loaded,0x7f800000 vout=1080p50hz,enable hdmimode=1080p50hz fb_width=1280 fb_height=720 panel_type=lcd_4
[BuildIn Slot 2]
cmd=ext4load mmc 1:6 1080000 /boot/kernel.img;bootm;
arg=\${bootargs} logo=osd0,loaded,0x7f800000 vout=1080p50hz,enable hdmimode=1080p50hz fb_width=1280 fb_height=720 panel_type=lcd_4
[BuildIn Slot 3]
cmd=ext4load mmc 1:7 1080000 /boot/kernel.img;bootm;
arg=\${bootargs} logo=osd0,loaded,0x7f800000 vout=1080p50hz,enable hdmimode=1080p50hz fb_width=1280 fb_height=720 panel_type=lcd_4
[BuildIn Slot 4]
cmd=ext4load mmc 1:8 1080000 /boot/kernel.img;bootm;
arg=\${bootargs} logo=osd0,loaded,0x7f800000 vout=1080p50hz,enable hdmimode=1080p50hz fb_width=1280 fb_height=720 panel_type=lcd_4
[   Recovery   ]
cmd=imgread kernel recovery \${loadaddr} 0; bootm \${loadaddr};
arg=\${bootargs}
EOF
  )
  echo "$new_content" > "$temp_file"
  mv "$temp_file" "$input_file"
  fi
fi

input_file="/data/bootconfig.txt"
temp_file="/data/bootconfig_temp.txt"
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

: exit 0