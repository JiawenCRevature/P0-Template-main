# This is the script that calculate average latency and success rate for Planetarium
httpRequestTotal=0
httpRequestFailure=0
httpStatusCodes=$(grep Response rolling.log | cut -f 2 -d ' ' | cut -f 2 -d [)
for status in $httpStatusCodes
do
	((httpRequestTotal++))
	if [ $status -ge 500 ]
	then 
		((httpRequestFailure++))
	fi
done
echo "The Total request amounts: $httpRequestTotal"
echo "The total failure times: $httpRequestFailure"
echo "The status codes: $httpStatusCodes"
httpSuccess=$(($httpRequestTotal - $httpRequestFailure))
httpAverage=$(awk "BEGIN {print $httpSuccess / $httpRequestTotal *100; exit}")
echo "HTTP success rate: $httpAverage%"

latercies=$(grep Response rolling.log | cut -f 2 -d : | cut -f 2 -d , | cut -f 4 -d ' ')
totalAmountOfLatericies=0
totalLatericies=0
for latency in $latercies
do
	((totalAmountOfLatericies++))
	totalLatericies=$(echo "scale=3; $totalLatericies + $latency" | bc)
done
averageLatericies=$(echo "scale=3; $totalLatericies / $totalAmountOfLatericies" | bc)
echo "The average lateracy is $averageLatericies ms"
