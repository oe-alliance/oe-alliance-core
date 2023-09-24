#!/bin/sh
### BEGIN INIT INFO
# Provides:           startup
# Required-Start:     $local_fs
# Required-Stop:      $local_fs
# Default-Start:      S
# Default-Stop:
# Short-Description:  dreambox gpt multiboot files
### END INIT INFO

is_mounted() {
  mount | grep "$1"
}

while ! is_mounted "/dev/dreambox-data"; do
  sleep 1
done

if [ ! -f "/data/STARTUP" ]; then
  cp -f /usr/share/startup/* /data
fi

input_file="/data/bootconfig.txt"
temp_file="/tmp/temp_file.txt"

if [ -e "$input_file" ]; then
  count_cmd=$(grep -o 'cmd=' "$input_file" | wc -l)
  if [ "$count_cmd" -ge 2 ]; then
  new_content=$(cat <<EOF
default=0
details=0
timeout=2
fb_pos=100,400
fb_size=1080,300
[Dreambox Image 1]
cmd=ext4load mmc 1:5 1080000 /boot/kernel.img;bootm;
arg=\${bootargs}
[Dreambox Image 2]
cmd=ext4load mmc 1:6 1080000 /boot/kernel.img;bootm;
arg=\${bootargs}
[Dreambox Image 3]
cmd=ext4load mmc 1:7 1080000 /boot/kernel.img;bootm;
arg=\${bootargs}
[Dreambox Recovery]
cmd=imgread kernel recovery \${loadaddr} 0; bootm \${loadaddr};
arg=\${bootargs}
EOF
  )
  echo "$new_content" > "$temp_file"
  mv "$temp_file" "$input_file"
  fi
fi

: exit 0