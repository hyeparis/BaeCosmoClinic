package com.douglas.bean;

public class ServiceDetail{
        private String Title;
        private String Category ;
        private String Description ;
        private String Thumbnail ;

        public ServiceDetail() {
        }

        public ServiceDetail(String title, String category, String description, String thumbnail) {
            Title = title;
            Category = category;
            Description = description;
            Thumbnail = thumbnail;
        }


        public String getTitle() {
            return Title;
        }

        public String getCategory() {
            return Category;
        }

        public String getDescription() {
            return Description;
        }

        public String getThumbnail() {
            return Thumbnail;
        }


        public void setTitle(String title) {
            Title = title;
        }

        public void setCategory(String category) {
            Category = category;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public void setThumbnail(String thumbnail) {
            Thumbnail = thumbnail;
        }
}
