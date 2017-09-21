#!/bin/bash
SERVICE_NAME=DRescueService
PATH_TO_JAR=/home/bitnami/drescue/drescue-server-0.1.jar
LOG_PATH=/home/bitnami/drescue/log.txt
PID_PATH_NAME=/tmp/DRescueService-pid
case $1 in
    start)
        echo "Starting $SERVICE_NAME ..."
        if [ ! -f $PID_PATH_NAME ]; then
            //nohup scala $PATH_TO_JAR /tmp 2>> /dev/null >> /dev/null &
            nohup scala $PATH_TO_JAR |& tee $LOG_PATH
                        echo $! > $PID_PATH_NAME
            echo "$SERVICE_NAME started ..."
        else
            echo "$SERVICE_NAME is already running ..."
        fi
    ;;
    stop)
        if [ -f $PID_PATH_NAME ]; then
            PID=$(cat $PID_PATH_NAME);
            echo "$SERVICE_NAME stoping ..."
            kill $PID;
            echo "$SERVICE_NAME stopped ..."
            rm $PID_PATH_NAME
        else
            echo "$SERVICE_NAME is not running ..."
        fi
    ;;
    restart)
        if [ -f $PID_PATH_NAME ]; then
            PID=$(cat $PID_PATH_NAME);
            echo "$SERVICE_NAME stopping ...";
            kill $PID;
            echo "$SERVICE_NAME stopped ...";
            rm $PID_PATH_NAME
            echo "$SERVICE_NAME starting ..."
            //nohup scala $PATH_TO_JAR /tmp 2>> /dev/null >> /dev/null &
            nohup scala $PATH_TO_JAR |& tee $LOG_PATH
                        echo $! > $PID_PATH_NAME
            echo "$SERVICE_NAME started ..."
        else
            echo "$SERVICE_NAME is not running ..."
        fi
    ;;
esac