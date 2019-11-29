#!/bin/bash

for i in {1..5}
do
    # запуск одного фонового процесса
    sleep 10 && echo $i &
done
# ожидание окончания работы
wait
echo Finished 
