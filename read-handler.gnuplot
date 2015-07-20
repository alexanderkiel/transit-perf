set terminal png size 1280,1024
set format y "%.0s"
set xrange [0:400]
set yrange [0:0.0001]
set ylabel "Time (µs)"
set xlabel "Number of Keywords Parsed"

set output "read-handler-ts.png"
plot "results/read-handler/cache-ts-1.csv" with yerrorbars ls 1, \
     "results/read-handler/cache-ts-1.csv" with lines ls 1, \
     "results/read-handler/switch-ts-1.csv" with yerrorbars ls 2, \
     "results/read-handler/switch-ts-1.csv" with lines ls 2
