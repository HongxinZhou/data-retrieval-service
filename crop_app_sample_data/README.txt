Crop App Sample Data

Project Directory Structure
crop_app_sample_data/
│
├── plant_type/
    ├── images/
    │   ├── img1.jpeg
    │   ├── img2.jpeg
    │   └── ...
    │
    └── labels.csv


Description
images/: This folder contains all the image files. Each image is named in the format imgX.jpeg, where X is a unique identifier for the image.

labels/labels.csv: This CSV file contains the corresponding labels for each image. For every image imgX.jpeg in the images/ directory, there is a corresponding entry in the labels.csv file.

CSV Structure
The labels.csv file has the following columns:

image_name: The name of the image file (e.g., img1.jpeg).
timestamp: The timestamp when the image was captured.
longitude: The longitude where the image was captured.
latitude: The latitude where the image was captured.
plant_type: The type of plant in the image.
soil_type: The type of soil in the image.
health_status: The health status of the plant in the image.
bounding_box_x1, bounding_box_y1, bounding_box_x2, bounding_box_y2: The coordinates of the bounding box around the plant in the image.
Usage
When working with an image from the images/ directory, ensure to refer to its corresponding label in the labels.csv file using the same unique identifier.