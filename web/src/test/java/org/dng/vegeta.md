
echo "GET http://localhost:8888/assets" | vegeta attack -workers=4 -max-workers=10 -duration=30s | tee results.bin | vegeta report
vegeta report -type=json results.bin > metrics.json
cat results.bin | vegeta plot > plot.html
cat results.bin | vegeta report -type="hist[0,100ms,200ms,300ms]"