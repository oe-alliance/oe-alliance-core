args=`cat /proc/cmdline`
for line in ${args};
do
     key=${line%%=*}
     value=${line#*=}
     if [ "$key" == "kernel" ]; then
          ln -sf "$value" /dev/kernel
     fi
done
