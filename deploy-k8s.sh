#!/bin/bash

echo "Applying Kubernetes namespace..."
kubectl apply -f infra/k8s/namespace.yaml

echo "Applying PostgreSQL PVC..."
kubectl apply -f infra/k8s/postgres-pvc.yaml

echo "Applying PostgreSQL deployment..."
kubectl apply -f infra/k8s/postgres-deployment.yaml

echo "Applying PostgreSQL service..."
kubectl apply -f infra/k8s/postgres-service.yaml

echo "Applying Admin Staff Service deployment..."
kubectl apply -f infra/k8s/admin-staff-service-deployment.yaml

echo "Applying Admin Staff Service service..."
kubectl apply -f infra/k8s/admin-staff-service-service.yaml

echo "Applying Student Service deployment..."
kubectl apply -f infra/k8s/student-service-deployment.yaml

echo "Applying Student Service service..."
kubectl apply -f infra/k8s/student-service-service.yaml

echo "Applying Teacher Service deployment..."
kubectl apply -f infra/k8s/teacher-service-deployment.yaml

echo "Applying Teacher Service service..."
kubectl apply -f infra/k8s/teacher-service-service.yaml

echo "Applying User Service deployment..."
kubectl apply -f infra/k8s/user-service-deployment.yaml

echo "Applying User Service service..."
kubectl apply -f infra/k8s/user-service-service.yaml

echo "Waiting for PostgreSQL to be ready..."
kubectl wait --for=condition=ready pod -l app=postgres -n distrischool --timeout=120s

echo "All manifests applied. Checking status..."
kubectl get all -n distrischool