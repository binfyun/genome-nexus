web: export GENOME_NEXUS_IMPORT_COMMIT=a32aae8f79cc1c215e864195973d273816677d4c && IMPORT_DIR=$(bash <(curl -L "https://github.com/genome-nexus/genome-nexus-importer/blob/${GENOME_NEXUS_IMPORT_COMMIT}/scripts/download_files_from_github_url.sh?raw=true") https://github.com/genome-nexus/genome-nexus-importer/tree/${GENOME_NEXUS_IMPORT_COMMIT}/export) && sleep 1s && bash $IMPORT_DIR/scripts/import_mongo.sh ${MONGODB_URI} && rm -rf $IMPORT_DIR && SERVER_PORT=${PORT} java $JAVA_OPTS -Dspring.data.mongodb.uri=${MONGODB_URI} -jar web/target/web-*.jar
