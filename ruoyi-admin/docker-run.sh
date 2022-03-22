

docker build -t ruoyi-plan:1.0.0 .

docker run -d -p 8080:8080 --name ruoyiplan -v /home/ruoyi/uploadPath:/home/ruoyi/uploadPath ruoyi-plan:1.0.0

