#include <stdio.h>
#include <stdlib.h>
#include <math.h>

const float camera_pitch = 54.0;
const int fov_h = 60;
const int fov_v = 40;
const int camera_left = 0; //x coordinate of the left side of the camera field of view
const int camera_right = 78; //x coodinate of the rightside of the camera field of view
const int camera_far = 0; //y coordinate of the far sige of the camera field of view
const int camera_near = 51; //y coordinate of the near side of the camera field of view
const float camera_height = 11.375;
const float centroid_x = 11.375;
const float centroid_y = 14.5;
const int tape_length = 18;
const float robot_width = 30.0;


float compute_y_floor_inches(int);
float compute_x_floor_inches(int, float);
float compute_camera_to_y_pixel_distance(float);
float print_table();

/** @brief prints a conversion table for every pixel on the camera
 */
float print_table()
{
    for (int y = camera_far; y <= camera_near; y++)
    {
        float y_inches = compute_y_floor_inches(y);
	y_inches = compute_camera_to_y_pixel_distance(y_inches);
	printf("%2.2f ", y_inches);
	for (int x = camera_left; x <= camera_right; x++)
	{
	    printf("%2.2f ", compute_x_floor_inches(x, y_inches)); 
	}
	printf("\n");
    }
}

/**
 * @brief Calculate the y distance in inches for a given camera pixel
 * @param[in] camera_y the y value of the pixel
 */
float compute_y_floor_inches(int camera_y)
{
    int camera_y_pixels = camera_near - camera_far;
    float degrees_per_pixel = (float)fov_v/(camera_y_pixels);
    float angle_to_pixel = (camera_pitch-(fov_v/2))+degrees_per_pixel*(camera_near-camera_y);
    float y_inches = tan(angle_to_pixel*3.14/180) * camera_height;
    //printf("camera_y pixels %d\n",camera_near-camera_far);
    //printf("degrees per y pixel %f\n",degrees_per_pixel);
    //printf("camera height %f angle to y pixel %f floor y inches %f\n", camera_height, angle_to_pixel, y_inches);
    //printf("y floor inches %f\n", y_inches);
    return y_inches;
}

/**
 * @brief 
 */
float compute_x_floor_inches(int camera_x, float y_pixel_dist)
{
    int camera_x_pixels = camera_right - camera_left;
    float degrees_per_pixel = (float)fov_h/(camera_x_pixels);
    float angle_to_pixel = -(fov_h/2) + degrees_per_pixel * camera_x;
    float x_inches = tan(angle_to_pixel*3.14/180) * y_pixel_dist;
    //printf("camera_x pixels %d\n", camera_right-camera_left);
    //printf("degrees per x pixel %f\n", degrees_per_pixel);
    //printf("y_inches %f angle to x pixel %f floor x inches %f\n", y_pixel_dist, angle_to_pixel, x_inches);
    //printf("x floor inches %f\n",x_inches);
    return x_inches;
}

float compute_camera_to_y_pixel_distance(float y_floor_distance)
{
    float camera_to_y_pixel_distance = sqrt(pow(camera_height,2)+pow(y_floor_distance,2));
    //printf("camera_y_to_pixel_distance %f\n", camera_to_y_pixel_distance);
    return camera_to_y_pixel_distance;
}
 

int main(int argc, char *argv[])
{
    if (argc == 1)
    {
        print_table();
    }
    else if (argc < 5)
    {
        printf("Usage enter two endpoints of a vector as x0 y0 x1 y1\n");
	printf("./a.out 12 0 36 12\n");
	exit(0);
    }  

    int v1x_p = atoi(argv[1]); //x in camera pixels for first vector
    int v1y_p = atoi(argv[2]); //y in camera pixels for first vector
    int v2x_p = atoi(argv[3]); //x in camera pixels for second vector
    int v2y_p = atoi(argv[4]); //y in camera pixels for second vector
    
    float v1y_fi = compute_y_floor_inches(v1y_p);
    float v1y_cpi = compute_camera_to_y_pixel_distance(v1y_fi);
    float v1x_fi = compute_x_floor_inches(v1x_p, v1y_cpi);
    float v2y_fi = compute_y_floor_inches(v2y_p);
    float v2y_cpi = compute_camera_to_y_pixel_distance(v2y_fi);
    float v2x_fi = compute_x_floor_inches(v2x_p, v2y_cpi);

    printf("camera\n");
    printf("vector p1 (%d,%d) => (%2.2f,%2.2f)\n", v1x_p, v1y_p, v1x_fi, v1y_fi);
    printf("vector p2 (%d,%d) => (%2.2f,%2.2f)\n", v2x_p, v2y_p, v2x_fi, v2y_fi);

    float v1y_ri = v1y_fi + centroid_y; //y distance from centroid of robot in inches for first vector
    float v1x_ri = v1x_fi + centroid_x; //x distance from centroid of robot in inches for first vector
    float v2y_ri = v2y_fi + centroid_y; //y distance from centroid of robot in inches for second vector
    float v2x_ri = v2x_fi + centroid_x; //x disntace from centroid of robot in inches for second vector

    printf("robot\n");
    printf("vector p1 (%d,%d) => (%2.2f,%2.2f)\n", v1x_p, v1y_p, v1x_ri, v1y_ri);
    printf("vector p2 (%d,%d) => (%2.2f,%2.2f)\n", v2x_p, v2y_p, v2x_ri, v2y_ri);


  const float robot_front_y = 15; // Distance from the centroid to the front of the robot
  const float robot_left_x = -(robot_width/2);
  const float robot_right_x = (robot_width/2);

  float pivot_distance = (float)sqrt(robot_right_x*robot_right_x + robot_front_y*robot_front_y);
  const int pivot_buffer = 2; // Safety buffer in inches to give the robot extra pivot clearance
  float target_offset = pivot_distance - tape_length + pivot_buffer; // Distance from the end of the tape to got to before pivot

  float slope = (v1y_ri - v2y_ri) / (v1x_ri - v2x_ri); //Slope of the vector
  float vector_angle = atan(slope)*(180/3.14);
        vector_angle += vector_angle <0? 180: 0;

  // Find a target point far enough from the wall that the robot can turn
  // Calculate commands to give to the robot to get into position
  float target_x = v2x_ri + (target_offset*sqrt(1/(1+slope*slope)));
  float target_y = v2y_ri + (slope*target_offset*sqrt(1/(1+slope*slope)));
  float target_angle = atan(target_x/target_y)*(180/3.14);
  float target_distance = sqrt(target_x*target_x + target_y*target_y);
  float target_to_vector_angle = (vector_angle) - (90 - target_angle);


/*
  printf("\nCamera coordinates\n");
  printf("(%2d,%2d)                               (%2d,%2d)\n\n\n", camera_left, camera_far, camera_right, camera_far);
  printf("(%2d,%2d)                               (%2d,%2d)\n\n\n", camera_left, camera_near, camera_right, camera_near);

  printf("\nCamera inches\n");
  printf("(%2.1f,%2.1f)                         (%2.1f,%2.1f)\n\n\n", compute_x_inches(0,compute_y_inches(0)), compute_y_inches(0),
		                                                      compute_x_inches(79, compute_y_inches(0)), compute_y_inches(0));
  printf("(%2.1f,%2.1f)                         (%2.1f,%2.1f)\n\n\n", compute_x_inches(0,compute_y_inches(51)), compute_y_inches(51),
		                                                      compute_x_inches(79,compute_y_inches(51)), compute_y_inches(51));
  printf("\nRobot inches\n");
  printf("(%2.1f,%2.1f)                         (%2.1f,%2.1f)\n\n\n", compute_x_inches(0,compute_y_inches(0))+centroid_x, compute_y_inches(0) +centroid_y,
		                                                      compute_x_inches(79, compute_y_inches(0))+centroid_x, compute_y_inches(0)+ centroid_y);
  printf("(%2.1f,%2.1f)                         (%2.1f,%2.1f)\n\n\n", compute_x_inches(0,compute_y_inches(51))+centroid_x, compute_y_inches(51)+centroid_y, 
		                                                      compute_x_inches(79, compute_y_inches(51))+centroid_x, compute_y_inches(51)+ centroid_y);
								      */
  printf("Pivot distance %f\n", pivot_distance);
  printf("Vector angle %2.1f\n", vector_angle);
  printf("slope = %f\n", slope);
  printf("Target offset %2.1f inches\n", target_offset);
  printf("Target (%2.1f,%2.1f)\n", target_x, target_y);
  printf("Target angle %2.1f\n", target_angle);
  printf("Target distance %2.1f\n",target_distance);
  printf("Target to Vector angle %2.1f\n", target_to_vector_angle);
  printf("\nRobot commands\n");
  printf("rotate(%2.1f)\n",target_angle);
  printf("drive_straight(%2.1f)\n",target_distance);
  printf("rotate(%2.1f)\n", -target_to_vector_angle);
  printf("drive_straight(%2.1f)\n",tape_length+target_offset-robot_front_y);

  return 0;
}
